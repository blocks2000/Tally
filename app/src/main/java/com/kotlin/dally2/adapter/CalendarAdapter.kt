package com.kotlin.dally2.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kotlin.dally2.R
import java.util.*

//历史账单界面，点击日历表后弹出的对话框中gridView的布局的适配器
class CalendarAdapter(var context: Context, var year: Int) : BaseAdapter() {
    var mDatas = ArrayList<String>()
    var selPos = -1


    init {
        loadData(year)
    }
    fun setsYear(year: Int) {
        this.year = year
        mDatas.clear() //将原来数据清空
        loadData(year)
        notifyDataSetChanged()
    }

    private fun loadData(year: Int) {
        for (i in 1..12) {
            val data = "$year/$i"
            mDatas.add(data)
        }
    }

    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): Any {
        return mDatas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view=LayoutInflater.from(context).inflate(R.layout.item_dialog_gv,parent,false)
        var g_tv:TextView=view.findViewById(R.id.item_dialog_gv_tv)
        g_tv.text =mDatas[position]
        g_tv.setBackgroundResource(R.color.grey_f3f3f3)
        g_tv.setTextColor(Color.BLACK)
        if (position == selPos) {  //传入的变量里面自带position
            g_tv.setBackgroundResource(R.color.pink_d6)
            g_tv.setTextColor(Color.WHITE)
        }
        return view
//        var convertView = convertView
//        convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_gv, parent, false)
//        val tv = convertView.findViewById<TextView>(R.id.item_dialog_gv_tv)
//        tv.text = mDatas[position]
//        tv.setBackgroundResource(R.color.grey_f3f3f3)
//        tv.setTextColor(Color.BLACK)
//        if (position == selPos) {  //传入的变量里面自带position
//            tv.setBackgroundResource(R.color.pink_d6)
//            tv.setTextColor(Color.WHITE)
//        }
//        return convertView
    }

}