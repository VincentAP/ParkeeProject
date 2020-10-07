package com.parkee.sendmoney.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.extensions.SharePrefExtensions
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.assets.repo.Const
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.TransferTypeRadioItemBinding

data class TransferTypeRadioItem (
    val typeHeader: String,
    val additionalText: String? = null,
    val estimatedTime: String
): BaseItem {
    override fun layoutId(): Int = R.layout.transfer_type_radio_item
    override fun id(): Long = typeHeader.hashCode().toLong()
}

class TransferTypeRadioItemViewHolder(private val itemBinding: TransferTypeRadioItemBinding):
    BaseViewHolder<TransferTypeRadioItem>(itemBinding.root) {
    private val sharePref =
        SharePrefExtensions(getContext(), Const.RADIO_BUTTON_KEY)

    override fun bind(item: TransferTypeRadioItem, listener: (BaseItem, View) -> Unit) {
        val currentChosen = sharePref.get(Const.CURRENT_SELECTED_RADIO_BUTTON_KEY, "Your balance")
        itemBinding.apply {
            radioButton.isChecked = currentChosen == item.typeHeader
            relativeTransferTypeRadioItemWrapper.setOnClickListener {
                radioButton.isChecked = true
                sharePref.put(Const.CURRENT_SELECTED_RADIO_BUTTON_KEY, item.typeHeader)
                listener(item, it)
            }
            textTransferTypeHeader.text = item.typeHeader
            textTransferTypeLongText.visibility = View.GONE
            item.additionalText?.let {
                textTransferTypeLongText.apply {
                    visibility = View.VISIBLE
                    text = it
                }
            }
            textNewToTransferWise.text =
                getContext().resources.getString(R.string.should_arrive_in, item.estimatedTime)
        }
    }
}

class TransferTypeRadioItemViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.transfer_type_radio_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as TransferTypeRadioItemViewHolder).bind(item as TransferTypeRadioItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return TransferTypeRadioItemViewHolder(
            TransferTypeRadioItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}