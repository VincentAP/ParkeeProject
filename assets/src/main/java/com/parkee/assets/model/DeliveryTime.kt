package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class DeliveryTime (
    @SerializedName("estimatedDeliveryDate")
    var estimatedDeliveryDate: String
)