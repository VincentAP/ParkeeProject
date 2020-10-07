package com.parkee.sendmoney.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.parkee.assets.extensions.SharePrefExtensions
import com.parkee.assets.extensions.animateReverse
import com.parkee.assets.extensions.fromJsonToList
import com.parkee.assets.foundations.BaseFragment
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseRecyclerAdapter
import com.parkee.assets.model.QuoteResponseMapResult
import com.parkee.assets.repo.Const
import com.parkee.assets.viewholder.LastItemDividerViewHolderFactory
import com.parkee.sendmoney.databinding.ChooseTransferTypeFragmentBinding
import com.parkee.sendmoney.viewholder.CancelTransferViewHolderFactory
import com.parkee.sendmoney.viewholder.TransferTypeHeaderViewHolderFactory
import com.parkee.sendmoney.viewholder.TransferTypeRadioItem
import com.parkee.sendmoney.viewholder.TransferTypeRadioItemViewHolderFactory
import com.parkee.sendmoney.viewmodel.ChooseTransferTypeViewModelImpl

class ChooseTransferTypeFragment: BaseFragment() {

    private var binding: ChooseTransferTypeFragmentBinding? = null
    private lateinit var chooseTransferTypeViewModel: ChooseTransferTypeViewModelImpl
    private val quoteItem by extraNotNull(QUOTE_ITEM, "{}")
    private lateinit var button: AppCompatButton
    private lateinit var seekBar: AppCompatSeekBar
    private lateinit var sharePref: SharePrefExtensions
    private val transferId by extraNotNull(TRANSFER_ID, 0)
    private val profileId by extraNotNull(PROFILE_ID, 0)
    private val listener: (BaseItem, View) -> Unit = { item, _ ->
        setOnClick(item)
    }
    private val recyclerAdapter = BaseRecyclerAdapter(
        listener, listOf(
            TransferTypeRadioItemViewHolderFactory(),
            TransferTypeHeaderViewHolderFactory(),
            LastItemDividerViewHolderFactory(),
            CancelTransferViewHolderFactory()
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChooseTransferTypeFragmentBinding.inflate(inflater, container, false)
        context?.let {
            sharePref =
                SharePrefExtensions(it, Const.RADIO_BUTTON_KEY)
        }
        button.visibility = View.VISIBLE
        binding?.recyclerChooseTransferType?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerAdapter
        }
        button.setOnClickListener {
            activity?.supportFragmentManager?.let { fm ->
                VerificationTransferFragment.newInstance(
                    profileId,
                    transferId
                )
                    .show(fm, VerificationTransferFragment.TAG)
            }
        }
        setupChooseTransferTypeViewModel()
        return binding?.root
    }

    private fun setupChooseTransferTypeViewModel() {
        chooseTransferTypeViewModel = ChooseTransferTypeViewModelImpl()
        chooseTransferTypeViewModel.getData().observe(this, Observer {
            recyclerAdapter.submitList(it)
        })
    }

    private fun setOnClick(item: BaseItem) {
        when(item) {
            is TransferTypeRadioItem -> recyclerAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        val itemList = quoteItem.fromJsonToList<QuoteResponseMapResult>()
        itemList?.get(0)?.let { quoteResp ->
            chooseTransferTypeViewModel.setData(
                quoteResp.fee,
                quoteResp.sourceCurrency,
                quoteResp.deliveryEstimate
            )
        }
    }

    override fun onBackPressed(): Boolean {
        seekBar.animateReverse(100, 75)
        sharePref.clear()
        return super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        sharePref.clear()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = ChooseTransferTypeFragment::class.java.name
        private const val QUOTE_ITEM = "QUOTE_ITEM"
        private const val PROFILE_ID = "PROFILE_ID"
        private const val TRANSFER_ID = "TRANSFER_ID"

        @JvmStatic
        fun newInstance(
            quoteItem: String?,
            button: AppCompatButton,
            seekBar: AppCompatSeekBar,
            transferId: Int,
            profileId: Int
        ) = ChooseTransferTypeFragment().apply {
            this.seekBar = seekBar
            this.button = button
            arguments = Bundle().apply {
                putString(QUOTE_ITEM, quoteItem)
                putInt(TRANSFER_ID, transferId)
                putInt(PROFILE_ID, profileId)
            }
        }
    }
}