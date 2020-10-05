package com.parkee.sendmoney.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.parkee.assets.foundations.BaseFragment

class SendMoneyViewPagerAdapter(
    activity: FragmentActivity,
    private val fragmentList: List<BaseFragment>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}