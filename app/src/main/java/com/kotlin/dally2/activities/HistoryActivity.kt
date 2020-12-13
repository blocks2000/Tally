package com.kotlin.dally2.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.dally2.R
import com.kotlin.dally2.adapter.AccountAdapter
import com.kotlin.dally2.db.AccountBean
import com.kotlin.dally2.db.DBManager
import com.kotlin.dally2.utils.CalendarDialog
import java.util.*

class HistoryActivity : AppCompatActivity() {
    lateinit var historyLv: ListView
    lateinit var timeTv: TextView
    var mDatas: MutableList<AccountBean>? = null
    var adapter: AccountAdapter? = null
    var year = 0
    var month = 0
    var selYear = -1
    var selMonth = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        //找到控件
        historyLv = findViewById(R.id.history_lv)
        timeTv = findViewById(R.id.history_tv_time)
//        mDatas = ArrayList()
        //设置适配器
        adapter = AccountAdapter(this, mDatas)
        historyLv.setAdapter(adapter)
        initTime()
        timeTv.setText(year.toString() + "年" + month + "月")
        loadData(year, month)
        setLVClickListener()
    }

    //设置ListView每一个view的长按点击事件
    private fun setLVClickListener() {
        historyLv!!.onItemLongClickListener = OnItemLongClickListener { parent, view, position, id ->
            val accountBean = mDatas!![position]
            deleteItem(accountBean)
            false
        }
    }

    //删除长按的item项
    private fun deleteItem(accountBean: AccountBean) {
        val id = accountBean.id
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示信息").setMessage("您确认要删除这条数据吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定") { dialog, which ->
                    DBManager.deleteAccItemById(id)
                    mDatas!!.remove(accountBean) //记得不仅要从数据库中删除，还要从数据源中删除
                    adapter!!.notifyDataSetChanged()
                }
        builder.create().show()
    }

    //获取指定年月的收支情况列表
    private fun loadData(year: Int, month: Int) {
        val list = DBManager.getAccountListOneMonth(year, month)
        mDatas!!.clear()
        mDatas!!.addAll(list)
        adapter!!.notifyDataSetChanged() //通知适配器数据发生变化
    }

    private fun initTime() {
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.history_iv_back -> finish()
            R.id.history_iv_calender -> {
                val cdialog = CalendarDialog(this, selYear, selMonth)
                cdialog.show()
                cdialog.setDialogSize()
                cdialog.setOnRefresh { selPos, year, month ->
                    timeTv!!.text = year.toString() + "月" + month + "月"
                    loadData(year, month)
                    selYear = selPos
                    selMonth = month
                }
            }
        }
    }
}