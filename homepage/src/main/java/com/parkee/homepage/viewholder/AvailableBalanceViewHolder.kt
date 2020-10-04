package com.parkee.homepage.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.extensions.loadImageFromLink
import com.parkee.assets.extensions.toCurrencyFormat
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.assets.repo.ImageLinkProvider
import com.parkee.homepage.R
import com.parkee.homepage.databinding.AvailableBalanceItemBinding
import de.hdodenhof.circleimageview.CircleImageView

data class AvailableBalanceItem(
    val amountValue: Double,
    val currency: String,
    val isLastItem: Boolean = false
): BaseItem {
    override fun layoutId(): Int = R.layout.available_balance_item
    override fun id(): Long = currency.hashCode().toLong()
}

class AvailableBalanceViewHolder(private val itemBinding: AvailableBalanceItemBinding)
    : BaseViewHolder<AvailableBalanceItem>(itemBinding.root) {
    override fun bind(item: AvailableBalanceItem, listener: (BaseItem, View) -> Unit) {
        if (item.isLastItem) itemBinding.viewRightMargin.visibility = View.VISIBLE
        ImageLinkProvider.getImageLink(item.currency)?.let {
            itemBinding.imageCountryFlag.loadImageFromLink(it)
        }
        itemBinding.textAmount.text = item.amountValue.toCurrencyFormat()
        itemBinding.textCurrencyType.text = getContext().resources.getString(R.string.currency_type, item.currency)
    }
}

class AvailableBalanceViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.available_balance_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as AvailableBalanceViewHolder).bind(item as AvailableBalanceItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return AvailableBalanceViewHolder(
            AvailableBalanceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}