package com.kotlin.dally2.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.kotlin.dally2.R
import com.kotlin.dally2.adapter.ChartAdapter
import com.kotlin.dally2.db.DBManager
import com.kotlin.dally2.frag_chart.IncomeChartFragment
import com.kotlin.dally2.frag_chart.OutcomeChartFragment
import com.kotlin.dally2.utils.CalendarDialog
import java.util.*
import kotlin.collections.ArrayList

class MothChartActivity : AppCompatActivity() {
    lateinit var inBtn: Button
    lateinit var outBtn: Button
    lateinit var dateTv: TextView
    lateinit var inTv: TextView
    lateinit var outTv: TextView
    lateinit var chartVp: ViewPager
    lateinit var back:ImageView
    lateinit var calender:ImageView
    var cyear = 0
    var cmonth = 0
    var selectPos = -1
    var selectMonth = -1
    lateinit var chartFragList: ArrayList<Fragment>
    private var incomeChartFragment: IncomeChartFragment? = null
    private var outcomeChartFragment: OutcomeChartFragment? = null
    private var chartAdapter: ChartAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moth_chart)
        initView() //找到控件
        initTime() //初始化时间
        initTopTextView(cyear, cmonth) //头布局文字展示
        initFrag() //吧收入和支出的fragment添加到布局里
//        setVPSelectListener()

        back.setOnClickListener { finish() }
        calender.setOnClickListener { showCalendarDialog() }
        inBtn.setOnClickListener {
            setButtonStyle(1)
            chartVp.currentItem = 1
        }
        outBtn.setOnClickListener {
            setButtonStyle(0)
            chartVp.currentItem = 0
        }

    }

    private fun setVPSelectListener() {
        chartVp.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                setButtonStyle(position)
            }
        })
    }

    private fun initFrag() {
        chartFragList = ArrayList()
        //添加Fragment的对象
        incomeChartFragment = IncomeChartFragment()
        outcomeChartFragment = OutcomeChartFragment()
        //添加数据到fragment中
        val bundle = Bundle()
        bundle.putInt("year", cyear)
        bundle.putInt("month", cmonth)
        incomeChartFragment!!.arguments = bundle
        outcomeChartFragment!!.arguments = bundle
        //将fragment添加到数据源中，支出在前，收入在后
        chartFragList.add(outcomeChartFragment!!)
        chartFragList.add(incomeChartFragment!!)
        //使用适配器,将fragment加载到activity当中
        chartAdapter = ChartAdapter(supportFragmentManager, chartFragList)
        chartVp.adapter = chartAdapter
    }

    private fun initTopTextView(year: Int, month: Int) {
        val inRoll = DBManager.getRollOneMonth(year, month, 1)
        val outRoll = DBManager.getRollOneMonth(year, month, 0)
        val inSum = DBManager.getSumMoneyOneMonth(year, month, 1)
        val outSum = DBManager.getSumMoneyOneMonth(year, month, 0)
        dateTv.text = year.toString() + "年" + month + "月账单"
        inTv.text = "共" + outRoll + "笔支出,￥ " + outSum
        outTv.text = "共" + inRoll + "笔收入,￥ " + inSum
    }

    //初始化时间
    private fun initTime() {
        val calendar = Calendar.getInstance()
        cyear = calendar[Calendar.YEAR]
        cmonth = calendar[Calendar.MONTH] + 1
    }

    private fun initView() {
        inBtn = findViewById(R.id.chart_btn_in)
        outBtn = findViewById(R.id.chart_btn_out)
        dateTv = findViewById(R.id.chart_tv_date)
        inTv = findViewById(R.id.chart_tv_in)
        outTv = findViewById(R.id.chart_tv_out)
        chartVp = findViewById(R.id.chart_vp)
        back=findViewById(R.id.chart_iv_back)
        calender=findViewById(R.id.chart_iv_calendar)
    }


    //显示日历的对话框
    private fun showCalendarDialog() {
        val dialog = CalendarDialog(this, selectPos, selectMonth)
        dialog.show()
        dialog.setDialogSize()
        dialog.setOnRefresh { selPos, year, month ->
            selectPos = selPos
            selectMonth = month
            incomeChartFragment!!.setData(year, month)
            outcomeChartFragment!!.setData(year, month)
            initTopTextView(year, month)
        }
    }

    //设置按钮样式的改变  支出为0  收入为1
    private fun setButtonStyle(kind: Int) {
        if (kind == 0) {
            outBtn.setBackgroundResource(R.drawable.main_recordbtn_bg)
            outBtn.setTextColor(Color.WHITE)
            inBtn.setBackgroundResource(R.drawable.dialog_btn_bg)
            inBtn.setTextColor(Color.BLACK)
        } else if (kind == 1) {
            inBtn.setBackgroundResource(R.drawable.main_recordbtn_bg)
            inBtn.setTextColor(Color.WHITE)
            outBtn.setBackgroundResource(R.drawable.dialog_btn_bg)
            outBtn.setTextColor(Color.BLACK)
        }
    }
}