package com.parkee.assets.extensions

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.parkee.assets.R
import de.hdodenhof.circleimageview.CircleImageView

fun Toolbar.setup(
    title: String,
    subtitle: String? = null,
    @DrawableRes backIconId: Int? = null,
    @DrawableRes logoId: Int? = null,
    imageUrl: String? = null,
    imageIcon: CircleImageView? = null,
    textTitle: TextView? = null,
    textSubtitle: TextView? = null,
    listener: () -> Unit
) {
    if (textTitle == null) {
        val color = ContextCompat.getColor(context, R.color.transferWiseWhite)
        this.apply {
            setTitleTextColor(color)
            setSubtitleTextColor(color)
            this.title = title
            subtitle?.let {
                this.subtitle = it
            }
        }
    } else {
        if (imageUrl == null) {
            logoId?.let {
                imageIcon?.apply {
                    visibility = View.VISIBLE
                    setImageDrawable(ContextCompat.getDrawable(context, it))
                }
            }
        }
        this.title = null
        textTitle.apply {
            text = title
        }
        subtitle?.let {
            textSubtitle?.apply {
                visibility = View.VISIBLE
                text = it
            }
        }
    }
    backIconId?.let {
        this.setNavigationIcon(it)
        this.setNavigationOnClickListener { listener.invoke() }
    }
}

fun View.animateClicked() {
    val animClicked = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
    val animClickedOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out)
    this.startAnimation(animClicked)
    this.startAnimation(animClickedOut)
}