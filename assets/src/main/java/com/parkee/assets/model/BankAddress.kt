package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class BankAddress (
    @SerializedName("addressFirstLine")
    var addressFirstLine: String,
    @SerializedName("postCode")
    var postCode: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("stateCode")
    var stateCode: String? = null
)