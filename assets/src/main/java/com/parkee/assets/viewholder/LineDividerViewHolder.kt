package com.parkee.assets.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.R
import com.parkee.assets.databinding.LineDividerItemBinding
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory

class LineDividerItem: BaseItem {
    override fun layoutId(): Int = R.layout.line_divider_item
    override fun id(): Long = layoutId().hashCode().toLong()
}

class LineDividerViewHolder(private val itemBinding: LineDividerItemBinding)
    : BaseViewHolder<LineDividerItem>(itemBinding.root) {
    override fun bind(item: LineDividerItem, listener: (BaseItem, View) -> Unit) {}
}

class LineDividerViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.line_divider_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as LineDividerViewHolder).bind(item as LineDividerItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return LineDividerViewHolder(
            LineDividerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}