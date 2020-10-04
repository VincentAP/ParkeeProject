package com.parkee.assets.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class TransferHistory (
    @SerializedName("id")
    var id: Int,
    @SerializedName("user")
    var user: Int,
    @SerializedName("targetAccount")
    var targetAccount: Int,
    @SerializedName("sourceAccount")
    var sourceAccount: Int? = null,
    @SerializedName("quote")
    var quote: Int,
    @SerializedName("status")
    var status: String,
    @SerializedName("reference")
    var reference: String,
    @SerializedName("rate")
    var rate: Double,
    @SerializedName("created")
    var created: String,
    @SerializedName("business")
    var business: Int? = null,
    @SerializedName("transferRequest")
    var transferRequest: Int? = null,
    @SerializedName("details")
    var details: Details,
    @SerializedName("hasActiveIssues")
    var hasActiveIssues: Boolean,
    @SerializedName("sourceValue")
    var sourceValue: Double,
    @SerializedName("sourceCurrency")
    var sourceCurrency: String,
    @SerializedName("targetValue")
    var targetValue: Double,
    @SerializedName("targetCurrency")
    var targetCurrency: String,
    @SerializedName("customerTransactionId")
    var customerTransactionId: UUID
)

data class TransferHistoryWrapper (
    val transferId: Int,
    val targetAccount: Int,
    val status: String,
    val sourceValue: Double,
    val sourceCurrency: String,
    val targetValue: Double,
    val targetCurrency: String,
    var createdDate: String,
    var estimatedDeliveryDate: String? = null,
    var accountHolderName: String? = null,
    var cancelledDate: String? = null,
    var createdDateInDate: Date? = null
)