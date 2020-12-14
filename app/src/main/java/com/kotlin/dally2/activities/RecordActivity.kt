package com.kotlin.dally2.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kotlin.dally2.R
import com.kotlin.dally2.adapter.RecordPagerAdapter
import com.kotlin.dally2.frag_record.IncomeFragment
import com.kotlin.dally2.frag_record.OutcomeFragment
import java.util.*
import kotlin.collections.ArrayList

class RecordActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    lateinit var back:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        //首先查找控件
        tabLayout = findViewById(R.id.record_tabs)
        viewPager = findViewById(R.id.record_vp)
        back=findViewById(R.id.record_iv_back)

        //再设置ViewPager加载页面
        initPager()
        //返回按钮的点击事件
        back.setOnClickListener { finish() }
    }

    private fun initPager() {
        //初始化ViewPager页面的集合
        val fragmentList: ArrayList<Fragment> = ArrayList()
        //创建收入和支出页面，放在fragment中
        val outfrg = OutcomeFragment() //支出
        val infrg = IncomeFragment() //收入
        fragmentList.add(outfrg)
        fragmentList.add(infrg)

        //创建适配器
        val pagerAdapter = RecordPagerAdapter(supportFragmentManager, fragmentList)
        //设置适配器
        viewPager!!.adapter = pagerAdapter
        //将tablayout和viewPager进行关联
        tabLayout!!.setupWithViewPager(viewPager)
    }


}