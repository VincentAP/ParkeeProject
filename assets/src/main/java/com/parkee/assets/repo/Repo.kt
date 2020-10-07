package com.parkee.assets.repo

import com.google.gson.annotations.SerializedName
import com.parkee.assets.model.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.http.Field
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

object Repo {
    internal var disposable: CompositeDisposable? = null

    fun login(): Single<List<User>> {
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .login(Const.HEADER_ATTR)
    }

    fun getAccountBalance(profileId: Int): Single<List<AvailableBalance>> {
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .getAccountBalance(
                Const.HEADER_ATTR,
                profileId
            )
    }

    fun getTransferHistory(
        offset: Int,
        limit: Int,
        profile: Int,
        status: String,
        createdDateStart: String,
        createdDateEnd: String
    ): Observable<List<TransferHistory>> {
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .getTransferHistory(
                Const.HEADER_ATTR,
                offset,
                limit,
                profile,
                status,
                createdDateStart,
                createdDateEnd
            )
    }

    fun getDeliveryTime(transferId: Int): Observable<DeliveryTime> {
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .getDeliveryTime(
                Const.HEADER_ATTR,
                transferId
            )
    }

    fun getRecipientAccountInfo(accountId: Int)
            : Observable<RecipientAccountInfo> {
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .getRecipientAccountInfo(
                Const.HEADER_ATTR,
                accountId
            )
    }

    fun getQuote(
        profile: String,
        source: String = "GBP",
        target: String = "CNY",
        rateType: String = "FIXED",
        sourceAmount: Double,
        type: String = "BALANCE_PAYOUT"
    ): Single<Quote> {
        val input = QuoteRaw(
            profile,
            source,
            target,
            rateType,
            sourceAmount,
            type
        )
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .getQuote(
                Const.HEADER_ATTR,
                input
            )
    }

    fun getQuoteTargetAmount(
        profile: String,
        source: String = "GBP",
        target: String = "GBP",
        rateType: String = "FIXED",
        targetAmount: Double,
        type: String = "BALANCE_PAYOUT"
    ): Single<Quote> {
        val input = QuoteRawTargetAmount(
            profile,
            source,
            target,
            rateType,
            targetAmount,
            type
        )
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .getQuoteTargetAmount(
                Const.HEADER_ATTR,
                input
            )
    }

    fun getAvailableCurrencyPairs(): Single<CurrencyPairs> {
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .getAvailableCurrencyPairs(Const.HEADER_ATTR)
    }

    fun getAvailableRecipient(
        profile: Int,
        currency: String
    ): Single<List<RecipientAccountInfo>> {
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .getAvailableRecipient(
                Const.HEADER_ATTR,
                profile,
                currency
            )
    }

    fun createRecipient(
        profile: Int,
        accountHolderName: String,
        currency: String,
        type: String = "chinese_alipay",
        legalType: String,
        accountNumber: String
    ): Single<CreateRecipientResponse> {
        val input = RecipientCreated(
            profile,
            accountHolderName,
            currency,
            type,
            CreateRecipientDetail(legalType, accountNumber)
        )
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .createRecipient(
                Const.HEADER_ATTR,
                input
            )
    }

    fun createRecipientByEmail(
        profile: Int,
        accountHolderName: String,
        currency: String,
        type: String = "email",
        email: String
    ): Single<RecipientAccountInfo> {
        val input = RecipientCreatedByEmail(
            profile,
            accountHolderName,
            currency,
            type,
            CreateRecipientDetailByEmail(email)
        )
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .createRecipientByEmail(
                Const.HEADER_ATTR,
                input
            )
    }

    fun createTransfer(
        targetAccount: Int,
        quote: Int,
        customerTransactionId: UUID,
        reference: String? = null,
        transferPurpose: String?,
        sourceOfFunds: String
    ): Single<TransferHistory> {
        val input = CreateTransferRequest(
            targetAccount,
            quote,
            customerTransactionId,
            CreateTransferRequestDetails(
                reference,
                transferPurpose,
                sourceOfFunds
            )
        )
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .createTransfer(
                Const.HEADER_ATTR,
                input
            )
    }

    fun fundTransfer(
        type: String = "BALANCE",
        profileId: Int,
        transferId: Int
    ): Single<FundTransferTypeResponse> {
        val input = FundTransferTypeRequest(type)
        return AuthenticationInterceptor()
            .getService(Const.BASE_URL)
            .fundTransfer(
                Const.HEADER_ATTR,
                profileId,
                transferId,
                input
            )
    }
}