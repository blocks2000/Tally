package com.kotlin.dally2.frag_chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.kotlin.dally2.R
import com.kotlin.dally2.adapter.ChartItemAdapter
import com.kotlin.dally2.db.ChartItemBean
import com.kotlin.dally2.db.DBManager
import java.util.*
import kotlin.collections.ArrayList

abstract class BaseChartFragment : Fragment() {
    lateinit var chartLv: ListView
    var mDatas: ArrayList<ChartItemBean>? = null
    var year = 0
    var month = 0
    private var adapter: ChartItemAdapter? = null
    lateinit var barChart //代表柱状图
            : BarChart
    var chartTv //如果没有收支情况，显示的文本控件
            : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_income_chart, container, false)
        chartLv = view.findViewById(R.id.frag_chart_lv)
        //获取从MothChartActivity用bundle封装传过来的数据
        val bundle = arguments
        year = bundle!!.getInt("year")
        month = bundle.getInt("month")
        //设置数据源
        mDatas = ArrayList()
        //设置适配器
        adapter = ChartItemAdapter(context, mDatas!!)
        chartLv.setAdapter(adapter)
        //添加柱状图头布局
        addLVHeaderView()
        return view
    }

    protected fun addLVHeaderView() {
        //将布局转换为View对象
        val headerView = layoutInflater.inflate(R.layout.item_chartfrag_top, null)
        //将View添加到listView的头布局上
        chartLv.addHeaderView(headerView)
        //查找头布局中的控件
        barChart = headerView.findViewById(R.id.item_chartfrag_chart)
        chartTv = headerView.findViewById(R.id.item_chartfrag_top_tv)
        //设定柱状图不显示描述
        barChart.getDescription().isEnabled = false
        //设置柱状图的内边距
        barChart.setExtraOffsets(20f, 20f, 20f, 20f)
        setAxis(year, month) //设置坐标轴
        setAxisData(year, month) //设置坐标轴显示的数据
    }

    //设置坐标轴显示的数据
    protected abstract fun setAxisData(year: Int, month: Int)

    //设置柱状图坐标轴的显示
    private fun setAxis(year: Int, month: Int) {
        //设置x轴
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM //设置x轴显示在下方
        xAxis.setDrawGridLines(true) //设置绘制该轴网格线
        xAxis.labelCount = 31 //设置x轴标签的个数
        xAxis.textSize = 12f //设置x轴标签的大小

        //设置x轴显示的值的格式
        xAxis.valueFormatter = IAxisValueFormatter { value, axis ->
            val data = value.toInt()
            if (data == 0) {
                return@IAxisValueFormatter "$month-1"
            }
            if (data== 14) {
                return@IAxisValueFormatter "$month-15"
            }
            //根据不同的月份，显示最后一天的位置不同
            if (month == 2) {
                if (data == 27) {
                    return@IAxisValueFormatter "$month-28"
                }
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                if (data == 29) return@IAxisValueFormatter "$month-30"
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                if (data == 30) return@IAxisValueFormatter "$month-31"
            }
            ""
        }
        xAxis.yOffset = 10f //设置标签对x轴的偏移量，垂直方向
        setYAxis(year, month) //y轴在子类中的设置
    }

    //设置y轴，因为最高的坐标不确定，所以在子类当中设置
    abstract fun setYAxis(year: Int, month: Int)
    open fun setData(year: Int, month: Int) {
        this.year = year
        this.month = month
        //清空柱状图当中的数据
        barChart.clear()
        barChart.invalidate() //重新绘制柱状图
        setAxis(year, month)
        setAxisData(year, month)
    }

    fun loadData(year: Int, month: Int, kind: Int) {
        val list = DBManager.getChartListFromAC(year, month, kind)
        mDatas!!.clear()
        mDatas!!.addAll(list)
        adapter!!.notifyDataSetChanged()
    }
}