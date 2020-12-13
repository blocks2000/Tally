package com.kotlin.dally2.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.dally2.R
import com.kotlin.dally2.adapter.AccountAdapter
import com.kotlin.dally2.db.AccountBean
import com.kotlin.dally2.db.DBManager
import java.util.*

class SearchActivity : AppCompatActivity() {
    var searchLv: ListView? = null
    var searchEt: EditText? = null
    var emptyTv: TextView? = null
    lateinit var mDatas //数据源
            : MutableList<AccountBean>
    var adapter //适配器
            : AccountAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        //先进行控件初始化
        initView()
        mDatas = ArrayList()
        adapter = AccountAdapter(this, mDatas as ArrayList<AccountBean>)
        searchLv!!.adapter = adapter
        searchLv!!.emptyView = emptyTv //设置无数据时，显示的控件
    }

    private fun initView() {
        searchLv = findViewById(R.id.search_lv)
        searchEt = findViewById(R.id.search_et)
        emptyTv = findViewById(R.id.search_tv_empty)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.search_iv_back -> finish()
            R.id.search_iv_sh -> {
                val msg = searchEt!!.text.toString().trim { it <= ' ' }
                //判断输入内容是否为空，为空则进行提醒
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(this, "输入内容不能为空！", Toast.LENGTH_SHORT).show()
                    return
                }
                //不为空的话，根据用户的输入进行模糊查询
                val list = DBManager.getACListtByComment(msg)
                mDatas!!.clear()
                mDatas!!.addAll(list)
                adapter!!.notifyDataSetChanged() //提示适配器数据更新
            }
        }
    }
}