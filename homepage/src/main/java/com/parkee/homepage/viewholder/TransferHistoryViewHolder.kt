package com.parkee.homepage.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.airbnb.paris.extensions.style
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.homepage.R
import com.parkee.homepage.databinding.TransferHistoryItemBinding

data class TransferHistoryItem (
    val RecipientName: String? = null,
    val transferStatus: String,
    val currencyFrom: String,
    val currencyTo: String,
    val isHistory: Boolean = false,
    val isCurrencyFromHasValue: Boolean = true
): BaseItem {
    override fun layoutId(): Int = R.layout.transfer_history_item
    override fun id(): Long = currencyTo.hashCode().toLong()
}

class TransferHistoryViewHolder(private val itemBinding: TransferHistoryItemBinding)
    : BaseViewHolder<TransferHistoryItem>(itemBinding.root) {
    private val titleHistoryColor =
        ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseHistoryTitleGrey)
    private val subTitleHistoryColor =
        ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseHistorySubtitleGrey)
    private val arrowHistoryColor =
        ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_right_history)

    override fun bind(item: TransferHistoryItem, listener: (BaseItem, View) -> Unit) {
        with(itemBinding) {
            if (item.isHistory)
                imageArrow.setImageDrawable(arrowHistoryColor)

            textRecipientName.apply {
                if (item.isHistory) setTextColor(titleHistoryColor)
                text = item.RecipientName
            }
            textTransferStatus.apply {
                if (item.isHistory) setTextColor(subTitleHistoryColor)
                text = item.transferStatus
            }
            textCurrencyFrom.apply {
                if (item.isHistory) setTextColor(titleHistoryColor)
                if (!item.isCurrencyFromHasValue) style(com.parkee.assets.R.style.TextView17_Grey61_Regular)
                text = item.currencyFrom
            }
            textCurrencyTo.apply {
                if (item.isHistory) setTextColor(subTitleHistoryColor)
                text = item.currencyTo
            }
        }
    }
}

class TransferHistoryViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.transfer_history_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as TransferHistoryViewHolder).bind(item as TransferHistoryItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return TransferHistoryViewHolder(
            TransferHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}