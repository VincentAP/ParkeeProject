package com.parkee.sendmoney.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.AvailableCurrenciesItemBinding

data class AvailableCurrencyItem (
    val currency: String,
    val countryName: String?,
    var isSelected: Boolean = false
): BaseItem {
    override fun layoutId(): Int = R.layout.available_currencies_item
    override fun id(): Long = currency.hashCode().toLong()
}

class AvailableCurrencyViewHolder (private val itemBinding: AvailableCurrenciesItemBinding)
    : BaseViewHolder<AvailableCurrencyItem>(itemBinding.root) {
    private val unselectedBG = ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseWhite)
    private val selectedBG = ContextCompat.getDrawable(getContext(), R.drawable.selected_currency_bg)

    private val unselectedText = ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseAccountBlue)
    private val selectedCurrencyText = ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseWhite)
    private val selectedCountryNameText = ContextCompat.getColor(getContext(), com.parkee.assets.R.color.transferWiseGrey61)

    override fun bind(item: AvailableCurrencyItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.apply {
            linearAvailableCurrenciesItemWrapper.apply {
                if (item.isSelected) background = selectedBG
                else setBackgroundColor(unselectedBG)
                setOnClickListener { listener(item, this) }
            }
            textCurrency.apply {
                if (item.isSelected) setTextColor(selectedCurrencyText)
                else setTextColor(unselectedText)
                text = item.currency
            }
            textCountry.apply {
                if (item.isSelected) setTextColor(selectedCountryNameText)
                else setTextColor(unselectedText)
                text = item.countryName
            }
        }
    }
}

class AvailableCurrencyViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.available_currencies_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as AvailableCurrencyViewHolder).bind(item as AvailableCurrencyItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return AvailableCurrencyViewHolder(
            AvailableCurrenciesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}