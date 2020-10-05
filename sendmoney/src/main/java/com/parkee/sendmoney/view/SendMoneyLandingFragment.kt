package com.parkee.sendmoney.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.parkee.assets.extensions.fromJsonToList
import com.parkee.assets.foundations.BaseFragment
import com.parkee.assets.foundations.BaseFullScreenDialogFragment
import com.parkee.assets.model.User
import com.parkee.sendmoney.adapter.SendMoneyViewPagerAdapter
import com.parkee.sendmoney.databinding.SendMoneyLandingFragmentBinding

class SendMoneyLandingFragment: BaseFullScreenDialogFragment() {

    private var binding: SendMoneyLandingFragmentBinding? = null
    private lateinit var fragmentList: MutableList<BaseFragment>
    private val tabTitle = listOf("International", "Same currency")
    private val item by extraNotNull(ITEM, "{}")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SendMoneyLandingFragmentBinding.inflate(inflater, container, false)
        initFragment()
        binding?.apply {
            layoutSendMoneyToolbar.imageClose.setOnClickListener { dismiss() }
            pagerSendMoney.adapter = activity?.let { act ->
                SendMoneyViewPagerAdapter(act, fragmentList, item)
            }

            TabLayoutMediator(tabSendMoney, pagerSendMoney) { tab, position ->
                tab.text = tabTitle[position]
                pagerSendMoney.setCurrentItem(tab.position, true)
            }.attach()
        }

        return binding?.root
    }

    private fun initFragment() {
        fragmentList = mutableListOf()
        fragmentList.add(SendMoneyInternationalFragment())
        fragmentList.add(SendMoneySameCurrencyFragment())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = SendMoneyLandingFragment::class.java.name
        private const val ITEM = "ITEM"

        @JvmStatic
        fun newInstance(item: String?) =
            SendMoneyLandingFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM, item)
                }
            }
    }
}