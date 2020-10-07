package com.parkee.sendmoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkee.assets.extensions.applyLastItemDecoration
import com.parkee.assets.foundations.BaseItem
import com.parkee.sendmoney.viewholder.CancelTransferItem
import com.parkee.sendmoney.viewholder.TransferTypeHeaderItem
import com.parkee.sendmoney.viewholder.TransferTypeRadioItem

interface ChooseTransferTypeViewModel {
    fun setData(
        fee: String,
        sourceCurrency: String,
        estimatedDelivery: String
    )
    fun getData(): LiveData<List<BaseItem>>
}

class ChooseTransferTypeViewModelImpl: ChooseTransferTypeViewModel, ViewModel() {
    private val data: MutableLiveData<List<BaseItem>> = MutableLiveData()

    override fun setData(
        fee: String,
        sourceCurrency: String,
        estimatedDelivery: String
    ) {
        val item: MutableList<BaseItem> = mutableListOf()
        item.add(
            TransferTypeHeaderItem(
                "Balance transfer",
                "$fee $sourceCurrency"
            )
        )
        item.add(
            TransferTypeRadioItem(
                "Your balance",
                null,
                estimatedDelivery
            )
        )
        item.add(
            TransferTypeHeaderItem(
                "Low cost transfer",
                "$fee $sourceCurrency"
            )
        )
        item.add(
            TransferTypeRadioItem(
                "Manually transfer the money from your bank",
                "Manually transfer the money to TransferWise using your bank.",
                estimatedDelivery
            )
        )
        item.add(
            TransferTypeRadioItem(
                "Authorise the payment from your bank account",
                "Authorise TransferWise to debit the money from your bank account. This option may not be supported by your bank yet.",
                estimatedDelivery
            )
        )
        item.add(
            TransferTypeHeaderItem(
                "Fast and easy transfer",
                "$fee $sourceCurrency"
            )
        )
        item.add(
            TransferTypeRadioItem(
                "Debit card",
                null,
                estimatedDelivery
            )
        )
        item.add(
            TransferTypeRadioItem(
                "Credit card",
                null,
                estimatedDelivery
            )
        )
        item.add(
            TransferTypeHeaderItem(
                "Advanced transfer",
                "$fee $sourceCurrency"
            )
        )
        item.add(
            TransferTypeRadioItem(
                "SWIFT transfer",
                "Send GBP from your bank account outside the UK. Your bank will charge you extra fees.",
                estimatedDelivery
            )
        )
        item.add(CancelTransferItem())
        item.applyLastItemDecoration()
        data.postValue(item)
    }

    override fun getData(): LiveData<List<BaseItem>> = data
}