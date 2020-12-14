package com.kotlin.dally2.frag_record

import com.kotlin.dally2.R
import com.kotlin.dally2.db.DBManager

class OutcomeFragment : BaseRecordFragment() {
    override fun loadDataToGv() {
        super.loadDataToGv()
        val outlist = DBManager.getTypeList(0)
        typeList.addAll(outlist)
        adapter!!.notifyDataSetChanged()
        typeTv.text = "其他"
        typeIv.setImageResource(R.mipmap.b_more)
    }

    override fun saveAccountToDB() {
        accountBean!!.kind = 0
        DBManager.insertItemToAcc(accountBean!!)
    }
}