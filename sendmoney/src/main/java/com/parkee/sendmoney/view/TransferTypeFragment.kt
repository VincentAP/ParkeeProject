package com.parkee.sendmoney.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseRecyclerAdapter
import com.parkee.assets.viewholder.HeaderSectionViewHolderFactory
import com.parkee.sendmoney.databinding.TransferTypeFragmentBinding
import com.parkee.sendmoney.viewholder.TransferTypeItem
import com.parkee.sendmoney.viewholder.TransferTypeViewHolderFactory
import com.parkee.sendmoney.viewmodel.TransferTypeViewModelImpl
import java.util.*

class TransferTypeFragment: BottomSheetDialogFragment() {

    private var binding: TransferTypeFragmentBinding? = null
    private lateinit var transferTypeViewModel: TransferTypeViewModelImpl
    private var onTransferTypeClicked: OnTransferTypeClicked? = null

    private val listener: (BaseItem, View) -> Unit = { item, _->
        setOnClick(item)
    }

    private val recyclerAdapter = BaseRecyclerAdapter(
        listener, listOf(
            HeaderSectionViewHolderFactory(),
            TransferTypeViewHolderFactory()
        )
    )

    fun setOnTransferTypeClicked(onTransferTypeClicked: OnTransferTypeClicked) = apply {
        this.onTransferTypeClicked = onTransferTypeClicked
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TransferTypeFragmentBinding.inflate(inflater, container, false)
        binding?.recyclerTransferType?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerAdapter
        }

        setupTransferTypeViewModel()
        return binding?.root
    }

    private fun setupTransferTypeViewModel() {
        transferTypeViewModel = TransferTypeViewModelImpl()
        transferTypeViewModel.getData().observe(this, Observer {
            recyclerAdapter.submitList(it)
        })
    }

    private fun setOnClick(item: BaseItem) {
        when(item) {
            is TransferTypeItem -> {
                onTransferTypeClicked?.onTransferTypeClicked(item.transferType)
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val feeItem = arguments?.getString(FEE_ITEM)
        val currencyItem = arguments?.getString(CURRENCY_ITEM)
        if (feeItem != null && currencyItem != null) {
            transferTypeViewModel.setData(feeItem, currencyItem)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    interface OnTransferTypeClicked {
        fun onTransferTypeClicked(type: String)
    }

    companion object {
        @JvmStatic
        val TAG: String = TransferTypeFragment::class.java.name
        private const val FEE_ITEM = "FEE_ITEM"
        private const val CURRENCY_ITEM = "CURRENCY_ITEM"

        @JvmStatic
        fun newInstance(
            feeItem: String?,
            currencyItem: String?
        ) =
            TransferTypeFragment().apply {
                arguments = Bundle().apply {
                    putString(FEE_ITEM, feeItem)
                    putString(CURRENCY_ITEM, currencyItem)
                }
            }
    }
}