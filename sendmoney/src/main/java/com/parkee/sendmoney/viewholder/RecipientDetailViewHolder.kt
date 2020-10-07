package com.parkee.sendmoney.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.RecipientDetailItemBinding

data class RecipientDetailItem (
    val id: Int,
    val initial: String,
    val name: String,
    val email: String? = null,
    val number: String
): BaseItem {
    override fun layoutId(): Int = R.layout.recipient_detail_item
    override fun id(): Long = number.hashCode().toLong()
}

class RecipientDetailViewHolder(private val itemBinding: RecipientDetailItemBinding)
    : BaseViewHolder<RecipientDetailItem>(itemBinding.root) {
    override fun bind(item: RecipientDetailItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.apply {
            textInitial2.text = item.initial
            textRecName.text = item.name
            item.email?.let {
                textRecEmail.apply {
                    visibility = View.VISIBLE
                    text = it
                }
            }
            textRecAttr.text = item.number
        }
    }
}

class RecipientDetailViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.recipient_detail_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as RecipientDetailViewHolder).bind(item as RecipientDetailItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return RecipientDetailViewHolder(
            RecipientDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}