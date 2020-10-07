package com.parkee.sendmoney.viewholder

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseViewHolder
import com.parkee.assets.foundations.ViewHolderFactory
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.BlueTextItemBinding

data class BlueTextItem(
    val text: String,
    val gravity: String? = null
): BaseItem {
    override fun layoutId(): Int = R.layout.blue_text_item
    override fun id(): Long = text.hashCode().toLong()
}

class BlueTextViewHolder(private val itemBinding: BlueTextItemBinding)
    : BaseViewHolder<BlueTextItem>(itemBinding.root) {
    override fun bind(item: BlueTextItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.textBlueAdditional.apply {
            text = setUnderlineText(item.text)
            gravity = if (item.gravity == "center") Gravity.CENTER
            else Gravity.START
        }
    }

    private fun setUnderlineText(text: String): SpannableString {
        val content = SpannableString(text)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        return content
    }
}

class BlueTextViewHolderFactory: ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.blue_text_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as BlueTextViewHolder).bind(item as BlueTextItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return BlueTextViewHolder(
            BlueTextItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}