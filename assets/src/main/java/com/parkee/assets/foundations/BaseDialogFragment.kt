package com.parkee.assets.foundations

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.parkee.assets.extensions.setup
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Vincent Ardyan Putra on 30/9/2020.
 */
open class BaseDialogFragment : DialogFragment() {

    open fun onBackPressed(): Boolean {
        return true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(context!!, theme) {
            override fun onBackPressed() {
                val backPressed = this@BaseDialogFragment.onBackPressed()
                if (backPressed) dismiss()
            }
        }
    }

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
            dismiss()
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