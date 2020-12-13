package com.kotlin.dally2.frag_record

import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kotlin.dally2.R
import com.kotlin.dally2.db.AccountBean
import com.kotlin.dally2.db.TypeBean
import com.kotlin.dally2.utils.CommentDialog
import com.kotlin.dally2.utils.KeyboardUtils
import com.kotlin.dally2.utils.SelectTimeDialog
import java.text.SimpleDateFormat
import java.util.*

//记录页面当中的支出模块
abstract class BaseRecordFragment : Fragment(), View.OnClickListener {
     lateinit var typeList: List<TypeBean>
    var keyboardView: KeyboardView? = null
    lateinit var moneyEt: EditText
    lateinit var typeIv: ImageView
    lateinit var typeTv: TextView
    lateinit var comment: TextView
    lateinit var timeTv: TextView
    lateinit var typeGv: GridView
    var adapter: TypeBaseAdapter? = null
    var accountBean: AccountBean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountBean = AccountBean()
        accountBean!!.typename="其他"
        accountBean!!.setsImageId(R.mipmap.more_fs)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_outcome, container, false)
        initView(view)
        //给gridView填充数据
        loadDataToGv()
        //给gridView中的每一项设置一个点击事件
        setGvListener()
        //初始化时间
        setInitTime()
        return view
    }

    private fun setInitTime() {
        val date = Date()
        //设置格式化形式
        val sdf = SimpleDateFormat("yyyy年MM月dd日 HH:mm")
        //再将得到的日期格式化
        val time = sdf.format(date)
        timeTv!!.text = time
        //加入到accountBean中
        accountBean?.time =time

        //获取年月日
        val calendar = Calendar.getInstance()
        val Year = calendar[Calendar.YEAR]
        val Month = calendar[Calendar.MONTH] + 1
        val Day = calendar[Calendar.DAY_OF_MONTH]

        //加入到accountBean中,这个保存的是系统自动生成的
        accountBean!!.year=Year
        accountBean!!.month=Month
        accountBean!!.day=Day
    }

    private fun setGvListener() {
        typeGv!!.onItemClickListener = OnItemClickListener { parent, view, position, id -> //把adapter里面的selectPos改为现在选中的位置，使其能够显示正确的颜色图片
            adapter!!.selectPos = position
            adapter!!.notifyDataSetChanged() //提示位置发生变化
            val typeBean = typeList!![position]
            val name = typeBean.typename
            typeTv!!.text = name
            //将数据插入accountBean中
            accountBean?.typename =name
            val simageId = typeBean.imageId
            typeIv!!.setImageResource(simageId)
            accountBean!!.setsImageId(simageId)
        }
    }

    //在子类中重载这个方法，以适应不同的fragment
    open fun loadDataToGv() {
        typeList = ArrayList()
        adapter = TypeBaseAdapter(context, typeList as ArrayList<TypeBean>)
        typeGv!!.adapter = adapter
    }

    private fun initView(view: View) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard)
        moneyEt = view.findViewById(R.id.frag_record_et_money)
        typeIv = view.findViewById(R.id.frag_record_iv)
        typeGv = view.findViewById(R.id.frag_record_gv)
        typeTv = view.findViewById(R.id.frag_record_tv_type)
        comment = view.findViewById(R.id.frag_record_tv_comment)
        timeTv = view.findViewById(R.id.frag_record_tv_time)
        comment.setOnClickListener(this)
        timeTv.setOnClickListener(this)
        //让自定义键盘显示出来
        val boardUtils = KeyboardUtils(keyboardView, moneyEt)
        boardUtils.showKeyboard()
        //设置接口，监听确定按钮被点击了
        boardUtils.setEnsureListener{ //获取输入钱数
            val moneyStr = moneyEt.getText().toString()
            //如果金钱标签为空或者为0，就不记录
            if (TextUtils.isEmpty(moneyStr) || moneyStr == "0") {
                activity!!.finish()
            }
            val money = moneyStr.toFloat()
            accountBean!!.money=money
            //获取记录信息，保存在数据库中
            //让子类实现
            saveAccountToDB()
            //返回上一级页面
            activity!!.finish()
        }
    }

    //让子类重写这个方法
    abstract fun saveAccountToDB()
    override fun onClick(v: View) {
        when (v.id) {
            R.id.frag_record_tv_time ->                 //如果用户自己设置了时间，则把用户设置的时间加入数据库
                showTimeDialog()
            R.id.frag_record_tv_comment -> showComment()
        }
    }

    private fun showTimeDialog() {
        val dialog = SelectTimeDialog(context!!)
        dialog.show()
        //设定确定按钮被点击了的监听器
        dialog.setOnEnsureListener { time, year, month, day ->
            timeTv!!.text = time
            accountBean?.time =time
            accountBean?.year=year
            accountBean?.month=month
            accountBean?.day=day
        }
    }

    private fun showComment() {
        val dialog = CommentDialog(context!!)
        dialog.show()
        dialog.setDialogSize()
        dialog.setOnEnsureListener {
            val msg = dialog.editText
            if (!TextUtils.isEmpty(msg)) {
                comment!!.text = msg
                accountBean?.comment=msg
            }
            dialog.cancel()
        }
    }

    companion object {
        private const val TAG = "realMonth"
    }
}