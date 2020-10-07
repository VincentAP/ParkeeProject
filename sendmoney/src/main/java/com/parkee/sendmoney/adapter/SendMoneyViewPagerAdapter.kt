package com.parkee.sendmoney.adapter

import androidx.appcompat.widget.AppCompatSeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.parkee.assets.foundations.BaseActivity
import com.parkee.assets.foundations.BaseFragment
import com.parkee.sendmoney.view.SendMoneyInternationalFragment
import com.parkee.sendmoney.view.SendMoneySameCurrencyFragment

class SendMoneyViewPagerAdapter(
    activity: BaseActivity,
    private val fragmentList: List<BaseFragment>,
    private val item: String
) : FragmentStateAdapter(activity) {

    private var itemClickedListener: ItemClickedListener? = null

    fun setOnItemClickedListener(itemClickedListener: ItemClickedListener) = apply {
        this.itemClickedListener = itemClickedListener
    }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                SendMoneyInternationalFragment
                    .newInstance(item)
                    .setOnSendMoneyInternationalListener(object :
                        SendMoneyInternationalFragment.OnSendMoneyInternationalListener {
                        override fun onSendMoneyInternationalListener(
                            item: String,
                            quoteItem: String?
                        ) {
                            itemClickedListener?.onItemClickedListener(
                                item,
                                quoteItem
                            )
                        }
                    })
            }
            else ->
                SendMoneySameCurrencyFragment.newInstance(item)
        }
    }

    interface ItemClickedListener {
        fun onItemClickedListener(
            item: String,
            quoteItem: String?
        )
    }
}