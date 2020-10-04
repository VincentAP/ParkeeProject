package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class Balances (
    @SerializedName("balanceType")
    var balanceType: String,
    @SerializedName("currency")
    var currency: String,
    @SerializedName("amount")
    var amount: Amount,
    @SerializedName("reservedAmount")
    var reservedAmount: Amount,
    @SerializedName("bankDetails")
    var bankDetails: List<BankDetails>? = null
)