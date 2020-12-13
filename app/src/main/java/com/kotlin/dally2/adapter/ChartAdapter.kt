package com.kotlin.dally2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ChartAdapter(fm: FragmentManager, var fragmentList: List<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

}