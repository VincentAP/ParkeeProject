package com.parkee.assets.extensions

import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.viewholder.LastItemDividerItem

fun MutableList<BaseItem>.applyLastItemDecoration() {
    this.add(LastItemDividerItem())
}