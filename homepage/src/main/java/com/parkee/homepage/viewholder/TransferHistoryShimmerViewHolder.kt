package com.parkee.homepage.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.homepage.R
import com.parkee.homepage.databinding.TransferHistoryShimmerItemBinding

class TransferHistoryShimmerItem: BaseItem {
    override fun layoutId(): Int = R.layout.transfer_history_shimmer_item
    override fun id(): Long = layoutId().hashCode().toLong()
}

class TransferHistoryShimmerViewHolder(itemBinding: TransferHistoryShimmerItemBinding)
    : BaseViewHolder<TransferHistoryShimmerItem>(itemBinding.root) {
    override fun bind(item: TransferHistoryShimmerItem, listener: (BaseItem, View) -> Unit) {
    }
}

class TransferHistoryShimmerViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.transfer_history_shimmer_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as TransferHistoryShimmerViewHolder).bind(item as TransferHistoryShimmerItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return TransferHistoryShimmerViewHolder(
            TransferHistoryShimmerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}