package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class RecipientAccountInfo (
    @SerializedName("id")
    var id: Int,
    @SerializedName("profile")
    var profile: Int,
    @SerializedName("accountHolderName")
    var accountHolderName: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("currency")
    var currency: String,
    @SerializedName("details")
    var details: RecipientAccountInfoDetails,
    @SerializedName("user")
    var user: Int,
    @SerializedName("active")
    var active: Boolean,
    @SerializedName("ownedByCustomer")
    var ownedByCustomer: Boolean
)