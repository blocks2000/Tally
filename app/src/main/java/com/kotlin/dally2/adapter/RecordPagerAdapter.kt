package com.kotlin.dally2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class RecordPagerAdapter(fm: FragmentManager, var fragmentList: List<Fragment>) : FragmentPagerAdapter(fm) {
    var titles = arrayOf("支出", "收入")
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}