package com.parkee.assets.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.R
import com.parkee.assets.databinding.DividerItemBinding
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory

class LastItemDividerItem : BaseItem {
    override fun layoutId(): Int = R.layout.divider_item
    override fun id(): Long = layoutId().hashCode().toLong()
}

class LastItemDividerViewHolder(itemBinding: DividerItemBinding) :
    BaseViewHolder<LastItemDividerItem>(itemBinding.root) {
    override fun bind(item: LastItemDividerItem, listener: (BaseItem, View) -> Unit) {}
}

class LastItemDividerViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.divider_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as LastItemDividerViewHolder).bind(item as LastItemDividerItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return LastItemDividerViewHolder(
            DividerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}