package com.parkee.sendmoney.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.AdditionalButtonItemBinding

data class AdditionalButtonItem (
    val color: String = "green"
): BaseItem {
    override fun layoutId(): Int = R.layout.additional_button_item
    override fun id(): Long = layoutId().hashCode().toLong()
}

class AdditionalButtonViewHolder(private val itemBinding: AdditionalButtonItemBinding)
    : BaseViewHolder<AdditionalButtonItem>(itemBinding.root) {
    private val blueBG = ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseBlue)
    private val greenBG = ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseGreen)

    override fun bind(item: AdditionalButtonItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.buttonContinue.apply {
            if (item.color == "blue") setBackgroundColor(blueBG)
            else setBackgroundColor(greenBG)
            setOnClickListener { listener(item, this) }
        }
    }
}

class AdditionalButtonViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.additional_button_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as AdditionalButtonViewHolder).bind(item as AdditionalButtonItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return AdditionalButtonViewHolder(
            AdditionalButtonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}