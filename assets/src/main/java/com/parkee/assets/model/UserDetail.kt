package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class UserDetail (
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("dateOfBirth")
    var dateOfBirth: String? = null,
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null,
    @SerializedName("avatar")
    var avatar: String? = null,
    @SerializedName("occupation")
    var occupation: String? = null,
    @SerializedName("occupations")
    var occupations: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("registrationNumber")
    var registrationNumber: String? = null,
    @SerializedName("acn")
    var acn: String? = null,
    @SerializedName("abn")
    var abn: String? = null,
    @SerializedName("arbn")
    var arbn: String? = null,
    @SerializedName("companyType")
    var companyType: String? = null,
    @SerializedName("companyRole")
    var companyRole: String? = null,
    @SerializedName("descriptionOfBusiness")
    var descriptionOfBusiness: String? = null,
    @SerializedName("primaryAddress")
    var primaryAddress: Int,
    @SerializedName("webpage")
    var webpage: String? = null,
    @SerializedName("businessCategory")
    var businessCategory: String? = null,
    @SerializedName("businessSubCategory")
    var businessSubCategory: String? = null
)