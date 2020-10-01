package com.parkee.assets.foundations

import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.parkee.assets.extensions.setup
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Vincent Ardyan Putra on 30/9/2020.
 */
open class BaseFragment : Fragment() {

    protected fun showShortToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }

    protected fun showShortToastByResId(stringResId: Int) {
        Toast.makeText(context, resources.getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToastByResId(stringResId: Int) {
        Toast.makeText(context, resources.getString(stringResId), Toast.LENGTH_LONG).show()
    }

    /**
     * override this and set it to false if you want to handle onBackPressed by yourself
     * by default this is set to true and will be handled by [BaseActivity.onBackPressed]
     */
    open fun onBackPressed(): Boolean = true

    protected fun Toolbar.add(
        title: String,
        subtitle: String? = null,
        @DrawableRes backIconId: Int? = null,
        @DrawableRes logoId: Int? = null,
        imageIcon: CircleImageView? = null,
        textTitle: TextView? = null,
        textSubtitle: TextView? = null
    ) {
        val activity = activity as BaseActivity
        activity.setSupportActionBar(this)
        this.setup(
            title,
            subtitle,
            backIconId,
            logoId,
            imageIcon = imageIcon,
            textTitle = textTitle,
            textSubtitle = textSubtitle
        ) {
            activity.onBackPressed()
        }
    }

    inline fun <reified T : Any> extra(key: String, default: T? = null) = lazy {
        val value = arguments?.get(key)
        if (value is T) value else default
    }

    inline fun <reified T : Any> extraNotNull(key: String, default: T? = null) = lazy {
        val value = arguments?.get(key)
        requireNotNull(if (value is T) value else default) { key }
    }
}