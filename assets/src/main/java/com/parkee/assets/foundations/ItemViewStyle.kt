package com.parkee.assets.foundations

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.paris.extensions.style
import com.airbnb.paris.styles.Style

open class ItemViewStyle<T : BaseItem> {

    private var containerStyle: Style? = null
    private var textViewStyle: Style? = null
    private var textViewStyleWithStyleResId: Int? = null

    @Suppress("UNCHECKED_CAST")
    fun setContainerStyle(viewGroupStyle: Style) = apply {
        containerStyle = viewGroupStyle
    } as T

    @Suppress("UNCHECKED_CAST")
    fun setTextStyleWithStyleResId(styleResId: Int) = apply {
        textViewStyleWithStyleResId = styleResId
    } as T

    @Suppress("UNCHECKED_CAST")
    fun setTextViewStyle(style: Style) = apply {
        textViewStyle = style
    } as T

    fun applyTextStyle(tv: AppCompatTextView) {
        textViewStyle?.let {
            tv.style(it)
        }
        textViewStyleWithStyleResId?.let {
            tv.style(it)
        }
    }

    fun applyContainerStyle(view: View) {
        containerStyle?.let {
            view.style(it)
        }
    }
}