package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class BankDetails (
    @SerializedName("id")
    var id: Int,
    @SerializedName("currency")
    var currency: String,
    @SerializedName("bankCode")
    var bankCode: String,
    @SerializedName("accountNumber")
    var accountNumber: String,
    @SerializedName("swift")
    var swift: String,
    @SerializedName("iban")
    var iban: String,
    @SerializedName("bankName")
    var bankName: String,
    @SerializedName("accountHolderName")
    var accountHolderName: String,
    @SerializedName("bankAddress")
    var bankAddress: List<BankAddress>
)