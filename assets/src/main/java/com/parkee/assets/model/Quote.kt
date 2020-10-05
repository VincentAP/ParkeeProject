package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class Quote (
    @SerializedName("id")
    var id: Int,
    @SerializedName("source")
    var source: String,
    @SerializedName("target")
    var target: String,
    @SerializedName("sourceAmount")
    var sourceAmount: Double,
    @SerializedName("targetAmount")
    var targetAmount: Double,
    @SerializedName("type")
    var type: String,
    @SerializedName("rate")
    var rate: Double,
    @SerializedName("createdTime")
    var createdTime: String,
    @SerializedName("createdByUserId")
    var createdByUserId: Int,
    @SerializedName("profile")
    var profile: Int,
    @SerializedName("rateType")
    var rateType: String,
    @SerializedName("deliveryEstimate")
    var deliveryEstimate: String,
    @SerializedName("fee")
    var fee: Double,
    @SerializedName("allowedProfileTypes")
    var allowedProfileTypes: List<ProfileType>,
    @SerializedName("guaranteedTargetAmount")
    var guaranteedTargetAmount: Boolean,
    @SerializedName("ofSourceAmount")
    var ofSourceAmount: Boolean
)

data class ProfileType (
    var type: String
)