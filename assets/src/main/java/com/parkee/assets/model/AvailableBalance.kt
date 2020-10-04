package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class AvailableBalance (
    @SerializedName("id")
    var id: Int,
    @SerializedName("profileId")
    var profileId: Int,
    @SerializedName("recipientId")
    var recipientId: Int,
    @SerializedName("creationTime")
    var creationTime: String,
    @SerializedName("modificationTime")
    var modificationTime: String,
    @SerializedName("active")
    var active: Boolean,
    @SerializedName("eligible")
    var eligible: Boolean,
    @SerializedName("balances")
    var balances: List<Balances>
)