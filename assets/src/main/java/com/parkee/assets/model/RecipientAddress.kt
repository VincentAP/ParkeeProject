package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class RecipientAddress (
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("countryCode")
    var countryCode: String? = null,
    @SerializedName("firstLine")
    var firstLine: String? = null,
    @SerializedName("postCode")
    var postCode: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("state")
    var state: String? = null
)