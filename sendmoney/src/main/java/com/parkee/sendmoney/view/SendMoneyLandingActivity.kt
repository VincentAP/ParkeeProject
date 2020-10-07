package com.parkee.sendmoney.view

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.parkee.assets.extensions.animate
import com.parkee.assets.foundations.BaseActivity
import com.parkee.assets.foundations.BaseFragment
import com.parkee.sendmoney.R
import com.parkee.sendmoney.adapter.SendMoneyViewPagerAdapter
import com.parkee.sendmoney.databinding.SendMoneyLandingActivityBinding
import com.parkee.sendmoney.navigator.SendMoneyNavigator

class SendMoneyLandingActivity: BaseActivity() {

    private lateinit var binding: SendMoneyLandingActivityBinding
    private lateinit var fragmentList: MutableList<BaseFragment>
    private val tabTitle = listOf("International", "Same currency")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SendMoneyLandingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment()
        binding.apply {
            layoutSendMoneyToolbar.imageClose.setOnClickListener { finish() }
            val item = intent.getStringExtra("ITEM")
            pagerSendMoney.adapter =
                SendMoneyViewPagerAdapter(
                    this@SendMoneyLandingActivity,
                    fragmentList,
                    item!!
                )
                    .setOnItemClickedListener(object : SendMoneyViewPagerAdapter.ItemClickedListener {
                        override fun onItemClickedListener(
                            item: String,
                            quoteItem: String?
                        ) {
                            binding.layoutSendMoneyToolbar.seekProgress.animate(0, 50)
                            CreateRecipientFragment().setSeekBar(binding.layoutSendMoneyToolbar.seekProgress)
                            SendMoneyNavigator.gotoCreateRecipientFragment(
                                supportFragmentManager,
                                R.id.frameFragmentContainer,
                                item,
                                quoteItem,
                                binding.layoutSendMoneyToolbar.seekProgress,
                                binding.buttonFinishAndPay
                            )
                        }
                    })

            TabLayoutMediator(tabSendMoney, pagerSendMoney) { tab, position ->
                tab.text = tabTitle[position]
                pagerSendMoney.setCurrentItem(tab.position, true)
            }.attach()

            fabHelp.setOnClickListener {
                HelpFragment.newInstance()
                    .show(supportFragmentManager, HelpFragment.TAG)
            }
        }
    }

    private fun initFragment() {
        fragmentList = mutableListOf()
        fragmentList.add(SendMoneyInternationalFragment())
        fragmentList.add(SendMoneySameCurrencyFragment())
    }
}