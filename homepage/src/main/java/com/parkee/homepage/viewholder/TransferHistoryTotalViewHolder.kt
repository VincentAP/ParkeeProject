package com.parkee.homepage.viewholder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.homepage.R
import com.parkee.homepage.databinding.TransferHistoryTotalItemBinding

data class TransferHistoryTotalItem (
    val total: Int
): BaseItem {
    override fun layoutId(): Int = R.layout.transfer_history_total_item
    override fun id(): Long = total.hashCode().toLong()
}

class TransferHistoryTotalViewHolder(private val itemBinding: TransferHistoryTotalItemBinding)
    : BaseViewHolder<TransferHistoryTotalItem>(itemBinding.root) {
    @SuppressLint("SetTextI18n")
    override fun bind(item: TransferHistoryTotalItem, listener: (BaseItem, View) -> Unit) {
        with(itemBinding) {
            textCurrentChosen.text = "1-${item.total}"
            textTotal.text = "${item.total}"
        }
    }
}

class TransferHistoryTotalViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.transfer_history_total_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as TransferHistoryTotalViewHolder).bind(item as TransferHistoryTotalItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return TransferHistoryTotalViewHolder(
            TransferHistoryTotalItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}