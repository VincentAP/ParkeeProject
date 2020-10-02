package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id")
    var id: Int,
    @SerializedName("type")
    var type: String,
    @SerializedName("details")
    var details: UserDetail
)