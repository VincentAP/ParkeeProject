package com.parkee.sendmoney.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.CancelTransferButtonItemBinding

class CancelTransferItem : BaseItem {
    override fun layoutId(): Int = R.layout.cancel_transfer_button_item
    override fun id(): Long = layoutId().hashCode().toLong()
}

class CancelTransferViewHolder(itemBinding: CancelTransferButtonItemBinding):
    BaseViewHolder<CancelTransferItem>(itemBinding.root) {
    override fun bind(item: CancelTransferItem, listener: (BaseItem, View) -> Unit) {}
}

class CancelTransferViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.cancel_transfer_button_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as CancelTransferViewHolder).bind(item as CancelTransferItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return CancelTransferViewHolder(
            CancelTransferButtonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}