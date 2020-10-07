package com.parkee.assets.repo

object PurposeProvider {
    private val transferPurpose = mapOf(
        "Buying property abroad" to "verification.transfers.purpose.purchase.property",
        "Rent or other property expenses" to "verification.transfers.purpose.pay.bills",
        "Mortgage payment" to "verification.transfers.purpose.mortgage",
        "Tuition fees or studying expenses" to "verification.transfers.purpose.pay.tuition",
        "Sending money home to family" to "verification.transfers.purpose.send.to.family",
        "General monthly living expenses" to "verification.transfers.purpose.living.expenses",
        "Salary payment" to "verification.transfers.purpose.other"
    )

    private val sourceOfFunds = mapOf(
        "Salary" to "verification.source.of.funds.salary",
        "Investments (stocks, properties, etc.)" to "verification.source.of.funds.investment",
        "Inheritance" to "verification.source.of.funds.inheritance",
        "Loan" to "verification.source.of.funds.loan",
        "Other" to "verification.source.of.funds.other"
    )

    fun getTransferPurpose(key: String): String? = transferPurpose[key]

    fun getSourceOfFunds(key: String): String? = sourceOfFunds[key]
}