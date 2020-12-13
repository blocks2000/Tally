package com.kotlin.dally2.frag_record

import com.kotlin.dally2.R
import com.kotlin.dally2.db.DBManager

class IncomeFragment : BaseRecordFragment() {
    override fun loadDataToGv() {
        super.loadDataToGv()
        val mlist=typeList.toMutableList()
        val inlist = DBManager.getTypeList(1)
        for (i in 0..inlist.size){
            mlist.add(inlist.get(i))
        }
        adapter!!.notifyDataSetChanged()
        typeIv!!.setImageResource(R.mipmap.p_more)
        typeTv!!.text = "其他"
    }

    override fun saveAccountToDB() {
        accountBean!!.kind = 1
        DBManager.insertItemToAcc(accountBean!!)
    }
}