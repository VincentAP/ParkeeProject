package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class CurrencyPairs (
    @SerializedName("sourceCurrencies")
    var sourceCurrencies: List<SourceCurrencies>,
    @SerializedName("total")
    var total: Int
)

data class SourceCurrencies (
    @SerializedName("currencyCode")
    var currencyCode: String,
    @SerializedName("maxInvoiceAmount")
    var maxInvoiceAmount: Double,
    @SerializedName("targetCurrencies")
    var targetCurrencies: List<TargetCurrencies>,
    @SerializedName("totalTargetCurrencies")
    var totalTargetCurrencies: Int
)

data class TargetCurrencies (
    @SerializedName("currencyCode")
    var currencyCode: String,
    @SerializedName("minInvoiceAmount")
    var minInvoiceAmount: Double,
    @SerializedName("fixedTargetPaymentAllowed")
    var fixedTargetPaymentAllowed: Boolean
)