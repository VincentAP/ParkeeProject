package com.parkee.sendmoney.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.TransferTypeHeaderItemBinding

data class TransferTypeHeaderItem (
    val headerText: String,
    val fee: String
): BaseItem {
    override fun layoutId(): Int = R.layout.transfer_type_header_item
    override fun id(): Long = headerText.hashCode().toLong()
}

class TransferTypeHeaderViewHolder(private val itemBinding: TransferTypeHeaderItemBinding):
    BaseViewHolder<TransferTypeHeaderItem>(itemBinding.root) {
    override fun bind(item: TransferTypeHeaderItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.apply {
            textTransferTypeHeader.text = item.headerText
            textTransferTypeFee.text =
                getContext().resources.getString(R.string.transfer_type_fee, item.fee)
        }
    }
}

class TransferTypeHeaderViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.transfer_type_header_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as TransferTypeHeaderViewHolder).bind(item as TransferTypeHeaderItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return TransferTypeHeaderViewHolder(
            TransferTypeHeaderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}