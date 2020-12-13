package com.kotlin.dally2.frag_record

import com.kotlin.dally2.R
import com.kotlin.dally2.db.DBManager

class OutcomeFragment : BaseRecordFragment() {
    override fun loadDataToGv() {
        super.loadDataToGv()
        val mlist=typeList.toMutableList()
        val outlist = DBManager.getTypeList(0)
        for (i in 0..outlist.size){
            mlist.add(outlist.get(i))
        }
        adapter!!.notifyDataSetChanged()
        typeTv!!.text = "其他"
        typeIv!!.setImageResource(R.mipmap.b_more)
    }

    override fun saveAccountToDB() {
        accountBean!!.kind = 0
        DBManager.insertItemToAcc(accountBean!!)
    }
}