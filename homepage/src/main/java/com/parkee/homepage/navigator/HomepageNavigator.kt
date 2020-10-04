package com.parkee.homepage.navigator

import androidx.fragment.app.FragmentManager
import com.parkee.assets.foundations.BaseNavigator
import com.parkee.homepage.view.fragment.HomeFragment

object HomepageNavigator: BaseNavigator() {
    fun gotoHomeFragment(
        fragmentManager: FragmentManager,
        container: Int,
        item: String?
    ) {
        navigateToFragment(
            fragmentManager,
            container,
            HomeFragment.newInstance(item),
            HomeFragment.TAG
        )
    }
}