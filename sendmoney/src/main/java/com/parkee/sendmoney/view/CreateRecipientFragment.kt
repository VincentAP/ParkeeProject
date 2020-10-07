package com.parkee.sendmoney.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.parkee.assets.extensions.animateReverse
import com.parkee.assets.extensions.fromJsonToList
import com.parkee.assets.foundations.BaseFragment
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseRecyclerAdapter
import com.parkee.assets.model.User
import com.parkee.assets.repo.Status
import com.parkee.assets.viewholder.HeaderSectionViewHolderFactory
import com.parkee.assets.viewholder.LastItemDividerViewHolderFactory
import com.parkee.assets.viewholder.LineDividerViewHolderFactory
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.CreateRecipientFragmentBinding
import com.parkee.sendmoney.navigator.SendMoneyNavigator
import com.parkee.sendmoney.viewholder.*
import com.parkee.sendmoney.viewmodel.CreateRecipientViewModelImpl

class CreateRecipientFragment: BaseFragment() {

    private var binding: CreateRecipientFragmentBinding? = null
    private lateinit var createRecipientViewModel: CreateRecipientViewModelImpl
    private val item1 by extraNotNull(ITEM, "{}")
    private val quoteItem by extraNotNull(QUOTE_ITEM, "{}")
    private var isFromCreateNewRecipient = false
    private var recipientId = 0
    private lateinit var button: AppCompatButton
    private lateinit var seekBar: AppCompatSeekBar

    private val listener: (BaseItem, View) -> Unit = { item, _ ->
        setOnClick(item)
    }
    private val recyclerAdapter = BaseRecyclerAdapter(
        listener, listOf(
            HeaderSectionViewHolderFactory(),
            CreateRecipientViewHolderFactory(),
            BlueTextViewHolderFactory(),
            LineDividerViewHolderFactory(),
            AdditionalButtonViewHolderFactory(),
            RecipientDetailViewHolderFactory(),
            LastItemDividerViewHolderFactory()
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateRecipientFragmentBinding.inflate(inflater, container, false)
        binding?.recyclerCreateRecipient?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerAdapter
        }
        setupCreateRecipientViewModel()
        return binding?.root
    }

    private fun setupCreateRecipientViewModel() {
        createRecipientViewModel = CreateRecipientViewModelImpl()
        createRecipientViewModel.getData().observe(this, Observer { (status, item) ->
            when(status) {
                Status.ON_PROGRESS -> {
                    binding?.coordinatorProgressBar?.visibility = View.VISIBLE
                    binding?.scrollCreateRecipientWrapper?.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding?.coordinatorProgressBar?.visibility = View.GONE
                    binding?.scrollCreateRecipientWrapper?.visibility = View.VISIBLE
                    recyclerAdapter.submitList(item)
                }
            }
        })
    }

    private fun setOnClick(item: BaseItem) {
        when(item) {
            is CreateRecipientItem -> {
                isFromCreateNewRecipient = true
                if (item.initial == "SE") {
                    activity?.supportFragmentManager?.let {
                        SendMoneyToSomeoneElseFragment.newInstance(item1)
                            .setOnRecipientCreated(object : SendMoneyToSomeoneElseFragment.OnRecipientCreated {
                                override fun onRecipientCreated(
                                    id: Int,
                                    accountHolderName: String,
                                    email: String?,
                                    aliPayNumber: String
                                ) {
                                    recipientId = id
                                    createRecipientViewModel.setCreatedRecipientDetail(
                                        id, accountHolderName, email, aliPayNumber
                                    )
                                }
                            })
                            .show(it, SendMoneyToSomeoneElseFragment.TAG)
                    }
                } else if (
                    item.name != "Business or charity" &&
                    item.name != "Myself"
                ) {
                    recipientId = item.id
                    createRecipientViewModel.setCreatedRecipientDetail(
                        item.id, item.name, item.email, item.number!!
                    )
                }
            }
            is AdditionalButtonItem -> {
                val itemList = item1.fromJsonToList<User>()
                itemList?.get(0)?.let { usr ->
                    activity?.supportFragmentManager?.let {
                        SendMoneyNavigator.gotoCreateTransferReasonFragment(
                            it,
                            R.id.frameFragmentContainer,
                            quoteItem,
                            recipientId,
                            seekBar,
                            button,
                            usr.id
                        )
                    }
                }
            }
        }
    }

    private fun refreshPage() {
        val itemList = item1.fromJsonToList<User>()
        itemList?.get(0)?.let {
            createRecipientViewModel.setData(
                it.id,
                "CNY",
                "${it.details.firstName?.get(0)}${it.details.lastName?.get(0)}"
            )
        }
    }

    override fun onResume() {
        super.onResume()
        refreshPage()
    }

    override fun onBackPressed(): Boolean {
        if (isFromCreateNewRecipient) {
            isFromCreateNewRecipient = false
            refreshPage()
            return false
        }
        SendMoneyInternationalFragment().setSeekBarReversed(50, seekBar)
        return super.onBackPressed()
    }

    fun setSeekBar(seekBar: AppCompatSeekBar) {
        this.seekBar = seekBar
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = CreateRecipientFragment::class.java.name
        private const val ITEM = "ITEM"
        private const val QUOTE_ITEM = "QUOTE_ITEM"

        @JvmStatic
        fun newInstance(
            item: String?,
            seekBar: AppCompatSeekBar,
            quoteItem: String?,
            button: AppCompatButton
        ) =
            CreateRecipientFragment().apply {
                this.seekBar = seekBar
                this.button = button
                arguments = Bundle().apply {
                    putString(ITEM, item)
                    putString(QUOTE_ITEM, quoteItem)
                }
            }
    }
}