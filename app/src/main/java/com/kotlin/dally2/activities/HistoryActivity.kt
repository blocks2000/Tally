package com.kotlin.dally2.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.dally2.R
import com.kotlin.dally2.adapter.normalAdapter
import com.kotlin.dally2.db.AccountBean
import com.kotlin.dally2.db.DBManager
import com.kotlin.dally2.utils.CalendarDialog
import java.util.*
import kotlin.collections.ArrayList

class HistoryActivity : AppCompatActivity() {
    lateinit var timeTv: TextView
    lateinit var historyLv:RecyclerView
    lateinit var back:ImageView
    lateinit var calendar:ImageView
    var mDatas= ArrayList<AccountBean>()
    lateinit var adapter: normalAdapter
    var year = 0
    var month = 0
    var selYear = -1
    var selMonth = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
//        //找到控件
        timeTv = findViewById(R.id.history_tv_time)
        initTime()
////        //设置适配器
        timeTv.setText(year.toString() + "年" + month + "月")
        loadData(year,month)
        val layoutManager=LinearLayoutManager(this)
        historyLv=findViewById(R.id.history_lv)
        historyLv.layoutManager=layoutManager
        val adapter=normalAdapter(mDatas)
        historyLv.adapter=adapter
        back=findViewById(R.id.history_iv_back)
        calendar=findViewById(R.id.history_iv_calender)
        back.setOnClickListener { finish() }
        calendar.setOnClickListener {
            val cdialog = CalendarDialog(this, selYear, selMonth)
            cdialog.show()
            cdialog.setDialogSize()
            cdialog.setOnRefresh { selPos, year, month ->
                    timeTv.text = year.toString() + "月" + month + "月"
                    mDatas.clear()
                    loadData(year, month)
                    adapter.notifyDataSetChanged()
                    selYear = selPos
                    selMonth = month
                }
        }
    }


    //获取指定年月的收支情况列表
    private fun loadData(year: Int, month: Int) {
        val list = DBManager.getAccountListOneMonth(year, month)
        for (accountBean in list){
            mDatas.add(accountBean)
        }
    }

    private fun initTime() {
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
    }

//    fun onClick(view: View) {
//        when (view.id) {
//            R.id.history_iv_back -> finish()
//            R.id.history_iv_calender -> {
//                val cdialog = CalendarDialog(this, selYear, selMonth)
//                cdialog.show()
//                cdialog.setDialogSize()
////                cdialog.setOnRefresh { selPos, year, month ->
////                    timeTv!!.text = year.toString() + "月" + month + "月"
////                    loadData(year, month)
////                    adapter.notifyDataSetChanged()
////                    selYear = selPos
////                    selMonth = month
////                }
//            }
//        }
//    }
}