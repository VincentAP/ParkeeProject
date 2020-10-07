package com.parkee.homepage.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.parkee.assets.extensions.fromJsonToList
import com.parkee.assets.foundations.BaseFragment
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseRecyclerAdapter
import com.parkee.assets.model.User
import com.parkee.assets.repo.Status
import com.parkee.assets.viewholder.HeaderSectionViewHolderFactory
import com.parkee.homepage.databinding.HomeFragmentBinding
import com.parkee.homepage.viewholder.*
import com.parkee.homepage.viewmodel.HomeFragmentViewModelImpl

class HomeFragment : BaseFragment() {

    private var binding: HomeFragmentBinding? = null
    private lateinit var homeFragmentViewModel: HomeFragmentViewModelImpl
    private val item by extraNotNull(ITEM, "{}")

    private val listenerHistory: (BaseItem, View) -> Unit = { item, view ->
        setOnClickHistory(item, view)
    }

    private val listenerBalance: (BaseItem, View) -> Unit = { item, view ->
        setOnClickBalance(item, view)
    }

    private val recyclerAdapterHistory = BaseRecyclerAdapter(
        listenerHistory, listOf(
            EmptyTransactionViewHolderFactory(),
            TransferHistoryViewHolderFactory(),
            HeaderSectionViewHolderFactory(),
            TransferHistoryShimmerViewHolderFactory(),
            TransferHistoryTotalViewHolderFactory(),
            FailedReloadViewHolderFactory()
        )
    )

    private val recyclerAdapterBalance = BaseRecyclerAdapter(
        listenerBalance, listOf(
            AvailableBalanceViewHolderFactory()
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding?.recyclerAccountBalance?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recyclerAdapterBalance
        }
        binding?.recyclerHistory?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerAdapterHistory
        }

        setupHomeFragmentViewModel()
        refreshPage()
        return binding?.root
    }

    private fun setupHomeFragmentViewModel() {
        homeFragmentViewModel = HomeFragmentViewModelImpl()

        homeFragmentViewModel.getAccountBalance().observe(this, Observer {
            recyclerAdapterBalance.submitList(it)
        })

        homeFragmentViewModel.getHistory().observe(this, Observer { (status, item) ->
            recyclerAdapterHistory.submitList(item)
            when(status) {
                Status.ON_PROGRESS -> {

                }
                Status.SUCCESS -> {

                }
                Status.SUCCEED_BUT_EMPTY -> {

                }
                Status.FAILED -> {

                }
            }
        })
    }

    private fun setOnClickHistory(item: BaseItem, view: View) {
        when(item) {
            is FailedReloadItem -> refreshPage()
        }
    }

    private fun setOnClickBalance(item: BaseItem, view: View) {

    }

    private fun refreshPage() {
        val itemList = item.fromJsonToList<User>()
        itemList?.get(0)?.id?.let {
            homeFragmentViewModel.setAccountBalance(it)
            homeFragmentViewModel.setHistory(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = HomeFragment::class.java.name
        private const val ITEM = "ITEM"

        @JvmStatic
        fun newInstance(item: String?) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM, item)
                }
            }
    }
}