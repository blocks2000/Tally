package com.kotlin.dally2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kotlin.dally2.R
import com.kotlin.dally2.db.AccountBean
import java.util.*

class AccountAdapter(var context: Context, var mDatas: MutableList<AccountBean>?) : BaseAdapter() {
    var inflater: LayoutInflater
    var year: Int
    var month: Int
    var day: Int
    override fun getCount(): Int {
        return mDatas!!.size
    }

    override fun getItem(position: Int): Any {
        return mDatas!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        var holder: ViewHolder? = null
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_mainlv, parent, false)
            holder = ViewHolder(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val accountBean = mDatas!![position]
        holder!!.typeIv.setImageResource(accountBean!!.getsImageId())
        holder.typeTv.text = accountBean?.typename
        holder.commentTv.text = accountBean?.comment
        holder.moneyTv.text = "￥" + accountBean?.money
        if (accountBean?.year == year && accountBean?.month == month && accountBean?.day == day) {
            val time = accountBean?.time?.split(" ".toRegex())?.toTypedArray()?.get(1)
            holder.timeTv.text = "今天$time"
        } else {
            holder.timeTv.text = accountBean?.time
        }
        return convertView
    }

    internal inner class ViewHolder(view: View) {
        var typeIv: ImageView
        var typeTv: TextView
        var commentTv: TextView
        var timeTv: TextView
        var moneyTv: TextView

        init {
            typeIv = view.findViewById(R.id.item_mainlv_iv)
            typeTv = view.findViewById(R.id.item_mainlv_tv_title)
            timeTv = view.findViewById(R.id.item_mainlv_tv_time)
            commentTv = view.findViewById(R.id.item_mainlv_tv_beizhu)
            moneyTv = view.findViewById(R.id.item_mainlv_tv_money)
        }
    }

    init {
        inflater = LayoutInflater.from(context)
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
        day = calendar[Calendar.DAY_OF_MONTH]
    }
}