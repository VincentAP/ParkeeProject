package com.parkee.sendmoney.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.parkee.assets.foundations.BaseFragment
import com.parkee.sendmoney.view.SendMoneyInternationalFragment
import com.parkee.sendmoney.view.SendMoneySameCurrencyFragment

class SendMoneyViewPagerAdapter(
    activity: FragmentActivity,
    private val fragmentList: List<BaseFragment>,
    private val item: String
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SendMoneyInternationalFragment.newInstance(item)
            else -> SendMoneySameCurrencyFragment.newInstance()
        }
    }
}