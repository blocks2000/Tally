package com.kotlin.dally2.frag_record

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kotlin.dally2.R
import com.kotlin.dally2.db.TypeBean

class TypeBaseAdapter(var context: Context?, var mData: List<TypeBean>) : BaseAdapter() {
    var selectPos = 0
    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): Any {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv, parent, false)
        //获取指定位置的数据源
        val typeBean = mData[position]
        //得到布局上的各个控件
        val item_iv = convertView.findViewById<ImageView>(R.id.item_recordfrag_iv)
        val item_tv = convertView.findViewById<TextView>(R.id.item_recordfrag_tv)
        item_tv.text = typeBean.typename
        //判断当前位置是否为选中位置
        if (selectPos == position) {
            item_iv.setImageResource(typeBean.imageId)
        } else {
            item_iv.setImageResource(typeBean.simageId)
        }
        return convertView
    }

}