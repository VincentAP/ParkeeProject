package com.parkee.assets.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.R
import com.parkee.assets.databinding.HeaderSectionItemBinding
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ItemViewStyle
import com.parkee.assets.foundations.ViewHolderFactory

data class HeaderSectionItem (
    val text: String
): BaseItem, ItemViewStyle<HeaderSectionItem>() {
    override fun layoutId(): Int = R.layout.header_section_item
    override fun id(): Long = text.hashCode().toLong()+1
}

class HeaderSectionViewHolder(private val itemBinding: HeaderSectionItemBinding)
    : BaseViewHolder<HeaderSectionItem>(itemBinding.root) {
    override fun bind(item: HeaderSectionItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.textHeaderSectionItem.apply {
            text = item.text
            item.applyTextStyle(this)
        }
        item.applyContainerStyle(itemBinding.linearHeaderSectionItemWrapper)
    }
}

class HeaderSectionViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.header_section_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as HeaderSectionViewHolder).bind(item as HeaderSectionItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return HeaderSectionViewHolder(
            HeaderSectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}