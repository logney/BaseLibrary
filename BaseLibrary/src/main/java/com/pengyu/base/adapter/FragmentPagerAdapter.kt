package com.pengyu.base.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

class FragmentPagerAdapter(fm: FragmentManager, private val mFragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

}