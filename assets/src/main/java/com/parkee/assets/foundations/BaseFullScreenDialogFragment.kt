package com.parkee.assets.foundations

import android.os.Bundle
import com.parkee.assets.R

open class BaseFullScreenDialogFragment : BaseDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
        setHasOptionsMenu(true)
    }
}