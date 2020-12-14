package com.kotlin.dally2.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kotlin.dally2.R
import com.kotlin.dally2.activities.HistoryActivity
import com.kotlin.dally2.db.AccountBean
import java.util.*
import kotlin.collections.ArrayList

class AccountAdapter(context:Context, mDatas: ArrayList<AccountBean>) : BaseAdapter() {
    private var context:Context?=null
    private var mDatas:ArrayList<AccountBean>?=null
    var year: Int
    var month: Int
    var day: Int
    init {
        this.context=context
        this.mDatas=mDatas
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
        day = calendar[Calendar.DAY_OF_MONTH]
    }
    override fun getCount(): Int {
        return mDatas!!.size
    }

    override fun getItem(position: Int): Any {
        return mDatas!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder:ViewHolder
        val view:View
        if (convertView==null){
            view= View.inflate(context,R.layout.item_mainlv,null)
            holder=ViewHolder(view)
            view.tag=holder
        }else{
            view=convertView
            holder=view.tag as ViewHolder
        }
        val accountBean = mDatas!![position]
        holder.typeIv.setImageResource(accountBean.getsImageId())
        holder.typeTv.text = accountBean.typename
        holder.commentTv.text = accountBean.comment
        holder.moneyTv.text = "￥" + accountBean?.money
        if (accountBean.year == year && accountBean.month == month && accountBean.day == day) {
            val time = accountBean.time?.split(" ".toRegex())?.toTypedArray()?.get(1)
            holder.timeTv.text = "今天$time"
        } else {
            holder.timeTv.text = accountBean.time
        }
        return view
    }

     class ViewHolder(view: View) {
        var typeIv: ImageView=view.findViewById(R.id.item_mainlv_iv)
        var typeTv: TextView= view.findViewById(R.id.item_mainlv_tv_title)
        var commentTv: TextView= view.findViewById(R.id.item_mainlv_tv_beizhu)
        var timeTv:TextView = view.findViewById(R.id.item_mainlv_tv_time)
        var moneyTv: TextView= view.findViewById(R.id.item_mainlv_tv_money)

    }


}