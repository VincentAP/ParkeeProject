package com.parkee.assets.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class CreateTransferRequest (
    var targetAccount: Int,
    var quote: Int,
    var customerTransactionId: UUID,
    var details: CreateTransferRequestDetails
)

data class CreateTransferRequestDetails (
    var reference: String? = null,
    var transferPurpose: String?,
    var sourceOfFunds: String
)

data class FundTransferTypeRequest (
    var type: String = "BALANCE"
)

data class FundTransferTypeResponse (
    @SerializedName("type")
    var type: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("errorCode")
    var errorCode: String? = null
)