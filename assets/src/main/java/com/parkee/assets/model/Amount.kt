package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class Amount(
    @SerializedName("value")
    var value: Double,
    @SerializedName("currency")
    var currency: String
)