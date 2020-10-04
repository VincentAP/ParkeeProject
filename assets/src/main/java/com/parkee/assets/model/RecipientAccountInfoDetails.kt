package com.parkee.assets.model

import com.google.gson.annotations.SerializedName

data class RecipientAccountInfoDetails (
    @SerializedName("address")
    var address: RecipientAddress,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("legalType")
    var legalType: String,
    @SerializedName("accountNumber")
    var accountNumber: String,
    @SerializedName("sortCode")
    var sortCode: String,
    @SerializedName("abartn")
    var abartn: String? = null,
    @SerializedName("accountType")
    var accountType: String? = null,
    @SerializedName("bankgiroNumber")
    var bankgiroNumber: String? = null,
    @SerializedName("ifscCode")
    var ifscCode: String? = null,
    @SerializedName("bsbCode")
    var bsbCode: String? = null,
    @SerializedName("institutionNumber")
    var institutionNumber: String? = null,
    @SerializedName("transitNumber")
    var transitNumber: String? = null,
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null,
    @SerializedName("bankCode")
    var bankCode: String? = null,
    @SerializedName("russiaRegion")
    var russiaRegion: String? = null,
    @SerializedName("routingNumber")
    var routingNumber: String? = null,
    @SerializedName("branchCode")
    var branchCode: String? = null,
    @SerializedName("cpf")
    var cpf: String? = null,
    @SerializedName("cardNumber")
    var cardNumber: String? = null,
    @SerializedName("idType")
    var idType: String? = null,
    @SerializedName("idNumber")
    var idNumber: String? = null,
    @SerializedName("idCountryIso3")
    var idCountryIso3: String? = null,
    @SerializedName("idValidFrom")
    var idValidFrom: String? = null,
    @SerializedName("idValidTo")
    var idValidTo: String? = null,
    @SerializedName("clabe")
    var clabe: String? = null,
    @SerializedName("swiftCode")
    var swiftCode: String? = null,
    @SerializedName("dateOfBirth")
    var dateOfBirth: String? = null,
    @SerializedName("clearingNumber")
    var clearingNumber: String? = null,
    @SerializedName("bankName")
    var bankName: String? = null,
    @SerializedName("branchName")
    var branchName: String? = null,
    @SerializedName("businessNumber")
    var businessNumber: String? = null,
    @SerializedName("province")
    var province: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("rut")
    var rut: String? = null,
    @SerializedName("token")
    var token: String? = null,
    @SerializedName("cnpj")
    var cnpj: String? = null,
    @SerializedName("payinReference")
    var payinReference: String? = null,
    @SerializedName("pspReference")
    var pspReference: String? = null,
    @SerializedName("orderId")
    var orderId: Int? = null,
    @SerializedName("idDocumentType")
    var idDocumentType: String? = null,
    @SerializedName("idDocumentNumber")
    var idDocumentNumber: String? = null,
    @SerializedName("targetProfile")
    var targetProfile: String? = null,
    @SerializedName("taxId")
    var taxId: String? = null,
    @SerializedName("iban")
    var iban: String? = null,
    @SerializedName("bic")
    var bic: String? = null,
    @SerializedName("IBAN")
    var IBAN: String? = null,
    @SerializedName("BIC")
    var BIC: String? = null,
    @SerializedName("interacAccount")
    var interacAccount: String? = null
)