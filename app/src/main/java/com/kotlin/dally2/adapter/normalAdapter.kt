package com.kotlin.dally2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.dally2.R
import com.kotlin.dally2.activities.HistoryActivity
import com.kotlin.dally2.db.AccountBean
import com.kotlin.dally2.db.DBManager
import java.util.*

class normalAdapter(var mdata:List<AccountBean>) : RecyclerView.Adapter<normalAdapter.InnerHolder>() {
    var year: Int
    var month: Int
    var day: Int
    init {
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
        day = calendar[Calendar.DAY_OF_MONTH]
    }
    class InnerHolder(view:View):RecyclerView.ViewHolder(view) {
        var typeIv: ImageView=view.findViewById(R.id.item_mainlv_iv)
        var typeTv: TextView= view.findViewById(R.id.item_mainlv_tv_title)
        var commentTv: TextView= view.findViewById(R.id.item_mainlv_tv_beizhu)
        var timeTv:TextView = view.findViewById(R.id.item_mainlv_tv_time)
        var moneyTv: TextView= view.findViewById(R.id.item_mainlv_tv_money)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): normalAdapter.InnerHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.item_mainlv,parent,false)
        val viewHolder= InnerHolder(view)
//        viewHolder.itemView.setOnLongClickListener {
//            val position=viewHolder.adapterPosition
//            val accountBean=mdata[position]
//            val id = accountBean.id
//            val builder = AlertDialog.Builder(parent.context)
//            builder.setTitle("提示信息").setMessage("您确认要删除这条数据吗？")
//                .setNegativeButton("取消", null)
//                .setPositiveButton("确定") { dialog, which ->
//                    DBManager.deleteAccItemById(id)
//                    //记得不仅要从数据库中删除，还要从数据源中删除
//                    adapter.notifyDataSetChanged()
//                }
//            builder.create().show()
//          }
          return viewHolder
    }



    override fun getItemCount(): Int {
        if(mdata!=null){
            return mdata.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: normalAdapter.InnerHolder, position: Int) {
         var accountBean=mdata[position]
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
    }

}