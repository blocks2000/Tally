package com.kotlin.dally2.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.dally2.R
import com.kotlin.dally2.adapter.AccountAdapter
import com.kotlin.dally2.adapter.normalAdapter
import com.kotlin.dally2.db.AccountBean
import com.kotlin.dally2.db.DBManager
import com.kotlin.dally2.utils.BudgetDialog
import com.kotlin.dally2.utils.mmoreDialog
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){
    lateinit var todayLv: ListView
    lateinit var searchIv: ImageView
    lateinit var editBtn: Button
    lateinit var moreBtn: ImageButton
     var mDatas: ArrayList<AccountBean>?=null
    lateinit var adapter:AccountAdapter
    var year = 0
    var month = 0
    var day = 0

    //头布局相关view
    lateinit var headerView: View
    lateinit var topOutTv: TextView
    lateinit var topInTv: TextView
    lateinit var topbudgetTv: TextView
    lateinit var topConTv: TextView
    lateinit var topShowIv: ImageView
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTime()
        initView()
        //初始化preference
        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE)
        //添加listView的头布局
        addLVHeaderView()

        headerView.setOnClickListener {
            val intent = Intent(this, MothChartActivity::class.java)
            startActivity(intent)
        }

        searchIv.setOnClickListener {
            val it = Intent(this, SearchActivity::class.java)
            startActivity(it)
        }

        editBtn.setOnClickListener {
            val it1 = Intent(this, RecordActivity::class.java)
            startActivity(it1) //跳转到记录界面
        }

        topShowIv.setOnClickListener {
            toggleShow()
        }

        topbudgetTv.setOnClickListener {
            showBudgetDialog()
        }

        moreBtn.setOnClickListener {
            val moreDialog = mmoreDialog(this)
            moreDialog.show()
            moreDialog.setDialogSize()
        }

        //要把数据放入mDatas
        mDatas = DBManager.getAccountListOneDay(year, month, day)
        //设置适配器，加载每一行数据到列表当中
        adapter = AccountAdapter(this, mDatas!!)
        todayLv.adapter = adapter
    }

    //初始化viw
    private fun initView() {
        todayLv = findViewById(R.id.main_lv)
        searchIv = findViewById(R.id.main_iv_search)
        editBtn = findViewById(R.id.main_btn_edit)
        moreBtn = findViewById(R.id.main_btn_more)

        setLVLongClickListener()
    }

    //设置ListView的长按事件
    private fun setLVLongClickListener() {
        todayLv.onItemLongClickListener = OnItemLongClickListener { parent, view, position, id ->
            if (position == 0) return@OnItemLongClickListener false //如果点击了头布局，就没有事件发生
            val pos = position - 1
            val clickBean = mDatas!![pos] //获取正在被点击的这条信息
            showDeleteItemDialog(clickBean)
            false
        }
    }

    //弹出是否删除某一条记录的对话框
    private fun showDeleteItemDialog(clickBean: AccountBean?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定") { dialog, which ->
                    //执行删除操作
                    //获取点击数据的id值，方便进行操作
                    val click_id = clickBean?.id
                    DBManager.deleteAccItemById(click_id!!)
                    mDatas!!.remove(clickBean) //实时刷新，移出集合当中的对象
                    adapter.notifyDataSetChanged() //提示适配器更新数据
                    setTopTvShow() //改变头布局显示的内容
                }
        builder.create().show() //注意定义好以后还要创建对话框和显示
    }

    //给listView添加头布局的方法
    private fun addLVHeaderView() {
        //将布局文件转换为view
        headerView = layoutInflater.inflate(R.layout.item_mainlv_top, null)
        todayLv.addHeaderView(headerView)
        //找到头布局的控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_tv_out)
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in)
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_budget)
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day)
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_iv_hide)

    }

    //获取今日具体时间
    private fun initTime() {
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
        day = calendar[Calendar.DAY_OF_MONTH]
    }

    //当activity获取焦点时，会调用的方法
    override fun onResume() {
        super.onResume()
        loadDBData()
        setTopTvShow()
    }

    //设置头布局当中文本内容的显示
    private fun setTopTvShow() {
        //获取今日收入和支出的总金额，显示在view中
        val outComeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 0)
        val inComeOneDay = DBManager.getSumMoneyOneDay(year, month, day, 1)
        val infoOneDay = "今日支出 ￥$outComeOneDay  收入 ￥$inComeOneDay"
        topConTv.text = infoOneDay

        //获取本月收入和支出总金额
        val outComeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0)
        val inComeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 1)
        topInTv.text = "￥$inComeOneMonth"
        topOutTv.text = "￥$outComeOneMonth"

        //设置显示剩余预算
        val bmoney = preferences.getFloat("bmoney", 0f) //获得预算
        if (bmoney == 0f) {
            topbudgetTv.text = "￥ 0"
        } else {
            val syMoney = bmoney - outComeOneMonth
            topbudgetTv.text = "￥ $syMoney"
        }
    }

    private fun loadDBData() {
        val list = DBManager.getAccountListOneDay(year, month, day)
        mDatas!!.clear()
        mDatas!!.addAll(list)
        adapter.notifyDataSetChanged()
    }


    //显示预算设置对话框
    private fun showBudgetDialog() {
        val dialog = BudgetDialog(this)
        dialog.show()
        dialog.setOnEnsureListener { money ->
            val editor = preferences.edit()
            editor.putFloat("bmoney", money)
            editor.commit()

            //计算剩余金额
            val outcomeOneMonth = DBManager.getSumMoneyOneMonth(year, month, 0)
            val syMoney = money - outcomeOneMonth //剩余预算=预算-支出
            topbudgetTv.text = "￥$syMoney"
        }
    }

    //点击头部的眼睛时，如果原来是明文，就加密，如果是密文，就显示
    var isShow = true
    private fun toggleShow() {
        if (isShow) {  //明文变密文
            val passwordMethod = PasswordTransformationMethod.getInstance()
            topInTv.transformationMethod = passwordMethod //可以将textView设置为隐藏
            topOutTv.transformationMethod = passwordMethod
            topbudgetTv.transformationMethod = passwordMethod
            topShowIv.setImageResource(R.mipmap.close)
            isShow = false //设置标志位为隐藏状态
        } else {  //密文变明文
            val hideMethod = HideReturnsTransformationMethod.getInstance()
            topInTv.transformationMethod = hideMethod //可以将textView设置为隐藏
            topOutTv.transformationMethod = hideMethod
            topbudgetTv.transformationMethod = hideMethod
            topShowIv.setImageResource(R.mipmap.show)
            isShow = true //设置标志位为显示状态
        }
    }

    companion object {
        private const val TAG = "date"
    }
}