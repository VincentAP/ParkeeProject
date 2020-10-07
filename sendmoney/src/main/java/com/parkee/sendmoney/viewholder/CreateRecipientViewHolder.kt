package com.parkee.sendmoney.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.CreateRecipientItemBinding

data class CreateRecipientItem (
    val id: Int,
    val initial: String,
    val name: String,
    val detail: String? = null,
    val email: String? = null,
    val number: String? = null
): BaseItem {
    override fun layoutId(): Int = R.layout.create_recipient_item
    override fun id(): Long = name.hashCode().toLong()
}

class CreateRecipientViewHolder(private val itemBinding: CreateRecipientItemBinding)
    : BaseViewHolder<CreateRecipientItem>(itemBinding.root) {
    override fun bind(item: CreateRecipientItem, listener: (BaseItem, View) -> Unit) {
        with(itemBinding) {
            linearCreateRecipientItemWrapper.setOnClickListener {
                listener(item, it)
            }
            textRecipientInitial.text = item.initial
            textName.text = item.name
            item.detail?.let {
                textDetail.apply {
                    visibility = View.VISIBLE
                    text = it
                }
            }
        }
    }
}

class CreateRecipientViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.create_recipient_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as CreateRecipientViewHolder).bind(item as CreateRecipientItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return CreateRecipientViewHolder(
            CreateRecipientItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}