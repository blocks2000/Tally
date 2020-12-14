package com.kotlin.dally2.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.kotlin.dally2.R
import com.kotlin.dally2.adapter.CalendarAdapter
import com.kotlin.dally2.db.DBManager
import java.util.*

class CalendarDialog : Dialog {
    var errorIv: ImageView
    var gv: GridView
    var hsvLayout: LinearLayout
    lateinit var hsvViewList: MutableList<TextView>
    lateinit var yearList: MutableList<Int>
    var selectPos = -1
    var selectMonth = -1
    var adapter: CalendarAdapter? = null
    private var onRefresh:((Int,Int,Int)->Unit)?=null

//    constructor(context: Context):this(context,0)

    constructor(context: Context,selectPos: Int, selectMonth: Int) : super(context){
        this.selectPos=selectPos
        this.selectMonth=selectMonth
        setContentView(R.layout.dialog_calender)
        errorIv = findViewById(R.id.dialog_calender_iv)
        gv = findViewById(R.id.dialog_calender_gv)
        hsvLayout = findViewById(R.id.dialog_calender_layout)
        errorIv.setOnClickListener{
            cancel()
        }
//        //向横向的ScrollView当中添加View的方法
        addViewToLayout()
        initGridView() //初始化gridView
        setGVListener()//设置gridView中每一项的点击事件
    }

    fun setOnRefresh(onRefresh:((Int,Int,Int)->Unit)){
        this.onRefresh=onRefresh
    }
//
//
//
////    override fun onCreate(savedInstanceState: Bundle) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.dialog_calender)
////        errorIv = findViewById(R.id.dialog_calender_iv)
////        gv = findViewById(R.id.dialog_calender_gv)
////        hsvLayout = findViewById(R.id.dialog_calender_layout)
////        errorIv.setOnClickListener{
////            cancel()
////        }
////        //向横向的ScrollView当中添加View的方法
////        addViewToLayout()
////        initGridView() //初始化gridView
////        setGVListener()//设置gridView中每一项的点击事件
////    }
//
    fun setGVListener() {
        gv.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            adapter!!.selPos = position
            adapter!!.notifyDataSetInvalidated()
            val month = position + 1
            val year = adapter!!.year
            //获取被选中的年份和月份,并且为了保持点过的记录，与HistoryActivity进行传递数据
            onRefresh?.invoke(selectPos,year,month)
            cancel()
        }
    }
//
    private fun initGridView() {
        val selYear = yearList[selectPos]
        adapter = CalendarAdapter(context, selYear)
        if (selectMonth == -1) {
            val month = Calendar.getInstance()[Calendar.MONTH]
            adapter!!.selPos = month
        } else {
            adapter!!.selPos = selectMonth - 1
        }
        gv.adapter = adapter
    }
//
    private fun addViewToLayout() {
    hsvViewList = ArrayList() //将要添加到现性布局当中的view进行统一管理
    yearList = DBManager.yearListFromAccounttb
    if (yearList!!.size == 0) {  //如果用户第一次使用，数据库中没有任何记录，则添加本年记录
        val year = Calendar.getInstance()[Calendar.YEAR]
        yearList!!.add(year)
    }


        //遍历年份列表，有几年就有几个textView
        for (i in yearList) {
            val year = i
            //将布局变为view对象，方便添加进另一个布局当中
            val view = layoutInflater.inflate(R.layout.item_dialog_hsv, null)
            hsvLayout.addView(view)
            val hsvTv = view.findViewById<TextView>(R.id.item_dialog_hsv_tv) //拿到布局中的textView
            hsvTv.text = year.toString() + ""
            hsvViewList.add(hsvTv)
        }

        //默认设置被选中的年份是当前年份
        if (selectPos == -1) {
            selectPos = hsvViewList.size - 1
        }
        changeTvbg(selectPos)
        setHSVClickListener() //设置每一个View的点击事件
    }
//
    //为scrollView中的项目设置点击事件
    private fun setHSVClickListener() {
        for (i in hsvViewList!!.indices) {
            val textView = hsvViewList!![i]
            val pos=i
            //如果此textView被点击
            textView.setOnClickListener {
                changeTvbg(pos)
                selectPos = pos
                //获取被选中的年份，下面的gridView中的年份会进行更改
                val year = yearList!![selectPos]
                adapter!!.setsYear(year)
            }
        }
    }

    //传入被选中的位置，改变位置上的背景和文字颜色
    private fun changeTvbg(selectPos: Int) {
        for (i in hsvViewList) {
            val view = i
            view.setBackgroundResource(R.drawable.dialog_btn_bg)
            view.setTextColor(Color.BLACK)
        }
        val selView = hsvViewList[selectPos]
        selView.setBackgroundResource(R.drawable.main_recordbtn_bg)
        selView.setTextColor(Color.WHITE)
    }
//


    //设置dialog的尺寸和屏幕尺寸一致
    fun setDialogSize() {
        //获取当前窗口对象
        val window = window
        //获取窗口对象参数
        val wlp = window!!.attributes
        //获取屏幕宽度
        val d = window.windowManager.defaultDisplay
        wlp.width = d.width
        wlp.gravity = Gravity.TOP //设置从顶部弹出
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.attributes = wlp
    }


    //接受从HistoryActivity传过来的数据（实际上是上次点击后接口传过去的数据），并对刚开始的selectPos和selectMonth进行初始化，让其不是固定的-1

}