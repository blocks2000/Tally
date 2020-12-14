package com.kotlin.dally2.frag_chart

import android.graphics.Color
import android.view.View
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.kotlin.dally2.db.DBManager
import java.util.*

class IncomeChartFragment : BaseChartFragment() {
    var kind = 1
    override fun onResume() {
        super.onResume()
        loadData(year, month, kind)
    }

    override fun setAxisData(year: Int, month: Int) {
        val sets: MutableList<IBarDataSet> = ArrayList()
        //获取这个月每天的支出金额
        val list = DBManager.getSumMoneyODIM(year, month, kind)
        if (list.size == 0) {
            barChart.visibility = View.GONE
            chartTv!!.visibility = View.VISIBLE
        } else {
            barChart.visibility = View.VISIBLE
            chartTv!!.visibility = View.GONE
            //设置有多少根柱子
            val barEntries: MutableList<BarEntry> = ArrayList()
            for (i in 0..30) {
                //初始化每一根柱子，添加到柱状图中
                val barEntry = BarEntry(i.toFloat(), 0.0f)
                barEntries.add(barEntry)
            }
            for (i in list.indices) {
                val itemBean = list[i]
                val day = itemBean.day //获取日期
                //根据日期，获取x轴的位置
                val xIndex = day - 1
                val barEntry = barEntries[xIndex] //获取到这天的这根柱子
                barEntry.y = itemBean.sumMoney //将此根柱子的高度设置为总金额高度
            }
            val barDataSet = BarDataSet(barEntries, "")
            barDataSet.valueTextColor = Color.BLACK
            barDataSet.valueTextSize = 8f
            barDataSet.color = Color.parseColor("#4ca18f") //柱子的颜色

            //设置柱子上数据显示的格式
            barDataSet.valueFormatter = IValueFormatter { value, entry, dataSetIndex, viewPortHandler -> //此处的value默认保存一位小数
                if (value == 0f) "" else value.toString() + ""
            }
            sets.add(barDataSet)
            val barData = BarData(sets)
            barData.barWidth = 0.2f //设置柱子的宽度
            barChart.data = barData
        }
    }

    override fun setYAxis(year: Int, month: Int) {
        //获取一个月中某一天收入最高的金额，设为y轴的最大值
        val maxMoney = DBManager.getMaxMoneyODInM(year, month, kind)
        val max = Math.ceil(maxMoney.toDouble()).toFloat() //将最大金额向上取整
        //设置y轴
        val yAxis_right = barChart.axisRight
        yAxis_right.axisMaximum = max
        yAxis_right.axisMinimum = 0f
        yAxis_right.isEnabled = false
        val yAxis_left = barChart.axisLeft
        yAxis_left.axisMaximum = max
        yAxis_left.axisMinimum = 0f
        yAxis_left.isEnabled = false

        //设置不显示图例
        val legend = barChart.legend
        legend.isEnabled = false
    }

    override fun setData(year: Int, month: Int) {
        super.setData(year, month)
        loadData(year, month, kind)
    }
}