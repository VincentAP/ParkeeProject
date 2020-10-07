package com.parkee.sendmoney.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.TransferTypeItemBinding

data class TransferTypeItem (
    val transferType: String,
    val transferFee: String,
    val transferMessage: String,
    val isChosen: Boolean = false
): BaseItem {
    override fun layoutId(): Int = R.layout.transfer_type_item
    override fun id(): Long = transferType.hashCode().toLong()
}

class TransferTypeViewHolder(private val itemBinding: TransferTypeItemBinding)
    : BaseViewHolder<TransferTypeItem>(itemBinding.root) {
    private val unselectedBG = ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseWhite)
    private val selectedBG = ContextCompat.getDrawable(getContext(), R.drawable.selected_currency_bg)

    private val unselectedText = ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseAccountBlue)
    private val selectedTransferText = ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseWhite)
    private val selectedRestText = ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseGrey61)

    override fun bind(item: TransferTypeItem, listener: (BaseItem, View) -> Unit) {
        with(itemBinding) {
            linearTransferTypeItemWrapper.apply {
                if (item.isChosen) background = selectedBG
                else setBackgroundColor(unselectedBG)
                setOnClickListener { listener(item, this) }
            }
            textTransferType.apply {
                if (item.isChosen) setTextColor(selectedTransferText)
                else setTextColor(unselectedText)
                text = item.transferType
            }
            textTransferFee.apply {
                if (item.isChosen) setTextColor(selectedRestText)
                else setTextColor(unselectedText)
                text = item.transferFee
            }
            textTransferMessage.apply {
                if (item.isChosen) setTextColor(selectedRestText)
                else setTextColor(unselectedText)
                text = item.transferMessage
            }
        }
    }
}

class TransferTypeViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.transfer_type_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as TransferTypeViewHolder).bind(item as TransferTypeItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return TransferTypeViewHolder(
            TransferTypeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}