package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class CreateRecipientResponse (
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
    var details: CreateRecipientDetail
)

data class RecipientCreated (
    val profile: Int,
    val accountHolderName: String,
    val currency: String,
    val type: String = "chinese_alipay",
    val details: CreateRecipientDetail
)

data class RecipientCreatedByEmail (
    val profile: Int,
    val accountHolderName: String,
    val currency: String,
    val type: String = "email",
    val details: CreateRecipientDetailByEmail
)

data class CreateRecipientDetailByEmail (
    val email: String
)

data class CreateRecipientDetail (
    @SerializedName("legalType")
    val legalType: String,
    @SerializedName("accountNumber")
    val accountNumber: String
)