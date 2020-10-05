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
    var allowedProfileTypes: List<String>,
    @SerializedName("guaranteedTargetAmount")
    var guaranteedTargetAmount: Boolean,
    @SerializedName("ofSourceAmount")
    var ofSourceAmount: Boolean
)

data class QuoteRaw(
    val profile: String,
    val source: String,
    val target: String,
    val rateType: String,
    val sourceAmount: Double,
    val type: String
)

data class QuoteResponseMapResult (
    var sourceCurrency: String,
    var targetCurrency: String,
    var sourceAmount: String,
    var targetAmount: String,
    var type: String,
    var subtractResult: String,
    var exchangeRate: String,
    var deliveryEstimate: String,
    var fee: String
)

data class BalanceForSpecificCurrency(
    val amount: Double,
    val currency: String = "GBP"
)