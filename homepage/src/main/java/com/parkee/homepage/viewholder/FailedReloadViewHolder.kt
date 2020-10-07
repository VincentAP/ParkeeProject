package com.parkee.homepage.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.homepage.R
import com.parkee.homepage.databinding.FailedReloadItemBinding

class FailedReloadItem: BaseItem {
    override fun layoutId(): Int = R.layout.failed_reload_item
    override fun id(): Long = layoutId().hashCode().toLong()
}

class FailedReloadViewHolder(private val itemBinding: FailedReloadItemBinding)
    : BaseViewHolder<FailedReloadItem>(itemBinding.root) {
    override fun bind(item: FailedReloadItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.buttonReload.setOnClickListener {
            listener(item, it)
        }
    }
}

class FailedReloadViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.failed_reload_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as FailedReloadViewHolder).bind(item as FailedReloadItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return FailedReloadViewHolder(
            FailedReloadItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}