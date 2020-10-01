package com.parkee.assets.foundations

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.parkee.assets.extensions.setup
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by Vincent Ardyan Putra on 30/9/2020.
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    protected fun showShortToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    protected fun showShortToastByResId(stringResId: Int) {
        Toast.makeText(this, resources.getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToastByResId(stringResId: Int) {
        Toast.makeText(this, resources.getString(stringResId), Toast.LENGTH_LONG).show()
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
        setSupportActionBar(this)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        this.setup(
            title,
            subtitle,
            backIconId,
            logoId,
            imageIcon = imageIcon,
            textTitle = textTitle,
            textSubtitle = textSubtitle
        ) {
            onBackPressed()
        }
    }

    open fun onBackPressedHandled(): Boolean = false

    override fun onBackPressed() {
        val fm = supportFragmentManager
        val count = fm.backStackEntryCount
        if (count == 0) {
            if (!onBackPressedHandled())
                super.onBackPressed()
        } else {
            val name = fm.getBackStackEntryAt(count - 1).name
            val currentFragment = fm.findFragmentByTag(name)
            var backPressedHandler = true
            if (currentFragment != null && currentFragment is BaseFragment) {
                backPressedHandler = currentFragment.onBackPressed()
            }
            if (backPressedHandler) fm.popBackStackImmediate()
        }
    }
}