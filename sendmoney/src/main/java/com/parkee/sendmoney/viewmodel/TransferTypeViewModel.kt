package com.parkee.sendmoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airbnb.paris.extensions.layoutMarginBottomDp
import com.airbnb.paris.extensions.layoutMarginTopDp
import com.airbnb.paris.extensions.viewGroupStyle
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.viewholder.HeaderSectionItem
import com.parkee.sendmoney.viewholder.TransferTypeItem

interface TransferTypeViewModel {
    fun setData(fee: String, currency: String)
    fun getData(): LiveData<List<BaseItem>>
}

class TransferTypeViewModelImpl: TransferTypeViewModel, ViewModel() {
    private val data: MutableLiveData<List<BaseItem>> = MutableLiveData()

    override fun setData(
        fee: String,
        currency: String
    ) {
        val item: MutableList<BaseItem> = mutableListOf()
        item.add(
            HeaderSectionItem("Transfer types")
                .setTextStyleWithStyleResId(com.parkee.assets.R.style.TextView17_LighterGrey_Bold)
                .setContainerStyle(viewGroupStyle {
                    layoutMarginTopDp(25)
                    layoutMarginBottomDp(20)
                })
        )
        item.add(
            TransferTypeItem(
                "Balance transfer",
                "- $fee $currency fee",
                "Send money from your $currency balance",
                true
            )
        )
        item.add(
            TransferTypeItem(
                "Low cost transfer",
                "- $fee $currency fee",
                "Send money from your bank account"
            )
        )
        item.add(
            TransferTypeItem(
                "Fast and easy transfer",
                "- $fee $currency fee",
                "Send money from your debit or credit card"
            )
        )
        item.add(
            TransferTypeItem(
                "Advanced transfer",
                "- $fee $currency fee",
                "Send from your $currency account outside the UK"
            )
        )
        data.postValue(item)
    }

    override fun getData(): LiveData<List<BaseItem>> = data
}