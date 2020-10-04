package com.parkee.homepage.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.homepage.R
import com.parkee.homepage.databinding.EmptyTranscationItemBinding

class EmptyTransactionItem: BaseItem {
    override fun layoutId(): Int = R.layout.empty_transcation_item
    override fun id(): Long = layoutId().hashCode().toLong()
}

class EmptyTransactionViewHolder(itemBinding: EmptyTranscationItemBinding)
    : BaseViewHolder<EmptyTransactionItem>(itemBinding.root) {
    override fun bind(
        item: EmptyTransactionItem,
        listener: (BaseItem, View) -> Unit
    ) {}
}

class EmptyTransactionViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.empty_transcation_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as EmptyTransactionViewHolder).bind(item as EmptyTransactionItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return EmptyTransactionViewHolder(
            EmptyTranscationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}