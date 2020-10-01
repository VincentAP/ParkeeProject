package com.parkee.assets.foundations

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

open class BaseNavigator {
    fun navigateToFragment(
        fragmentManager: FragmentManager,
        containerId: Int,
        fragment: Fragment,
        tag: String,
        stackType: String = REPLACE
    ) {
        fragmentManager
            .beginTransaction()
            .apply {
                when (stackType) {
                    ADD -> this.add(containerId, fragment, tag)
                    else -> this.replace(containerId, fragment, tag)
                        .addToBackStack(fragment.javaClass.name)
                }
            }.commit()
    }

    companion object {
        const val ADD = "ADD"
        const val REPLACE = "REPLACE"
    }
}