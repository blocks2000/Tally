package com.kotlin.dally2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kotlin.dally2.R
import com.kotlin.dally2.db.ChartItemBean
import com.kotlin.dally2.utils.FloatUtils

class ChartItemAdapter(var context: Context?, var mDatas: List<ChartItemBean>) : BaseAdapter() {
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
        val view:View
        var holder: ViewHolder? = null
        if (convertView == null) {
            view = View.inflate(context,R.layout.item_chartfrag_lv,null )
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view=convertView
            holder = view.tag as ViewHolder
        }

        //获取显示内容
        val bean = mDatas[position]
        holder.iv.setImageResource(bean.getsImageId())
        holder.typeTv.text = bean.type
        val ratio = bean.ratio
        val pert = FloatUtils.ratioToPercent(ratio)
        holder.ratioTv.text = pert
        holder.totalTv.text = "￥ " + bean.totalMoney
        return view
    }

    internal inner class ViewHolder(view: View) {
        var typeTv: TextView
        var ratioTv: TextView
        var totalTv: TextView
        var iv: ImageView

        init {
            typeTv = view.findViewById(R.id.item_chartfrag_ty_type)
            ratioTv = view.findViewById(R.id.item_chartfrag_tv_percent)
            totalTv = view.findViewById(R.id.item_chartfrag_tv_sum)
            iv = view.findViewById(R.id.item_chartfrag_iv)
        }
    }

}