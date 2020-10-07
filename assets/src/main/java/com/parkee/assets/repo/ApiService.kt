package com.parkee.assets.repo

import com.parkee.assets.model.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiService {

    @GET("v1/profiles")
    fun login(
        @Header("Authorization") authHeader: String
    ): Single<List<User>>

    @GET("v1/borderless-accounts")
    fun getAccountBalance(
        @Header("Authorization") authHeader: String,
        @Query("profileId") profileId: Int
    ): Single<List<AvailableBalance>>

    @GET("v1/transfers")
    fun getTransferHistory(
        @Header("Authorization") authHeader: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("profile") profile: Int,
        @Query("status") status: String,
        @Query("createdDateStart") createdDateStart: String,
        @Query("createdDateEnd") createdDateEnd: String
    ): Observable<List<TransferHistory>>

    @GET("v1/delivery-estimates/{transferId}")
    fun getDeliveryTime(
        @Header("Authorization") authHeader: String,
        @Path("transferId") transferId: Int
    ): Observable<DeliveryTime>

    @GET("v1/accounts/{accountId}")
    fun getRecipientAccountInfo(
        @Header("Authorization") authHeader: String,
        @Path("accountId") accountId: Int
    ): Observable<RecipientAccountInfo>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("v1/quotes")
    fun getQuote(
        @Header("Authorization") authHeader: String,
        @Body input: QuoteRaw
    ): Single<Quote>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("v1/quotes")
    fun getQuoteTargetAmount(
        @Header("Authorization") authHeader: String,
        @Body input: QuoteRawTargetAmount
    ): Single<Quote>

    @GET("v1/currency-pairs")
    fun getAvailableCurrencyPairs(
        @Header("Authorization") authHeader: String
    ): Single<CurrencyPairs>

    @GET("v1/accounts")
    fun getAvailableRecipient(
        @Header("Authorization") authHeader: String,
        @Query("profile") profile: Int,
        @Query("currency") currency: String
    ): Single<List<RecipientAccountInfo>>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("v1/accounts")
    fun createRecipient(
        @Header("Authorization") authHeader: String,
        @Body input: RecipientCreated
    ): Single<CreateRecipientResponse>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("v1/accounts")
    fun createRecipientByEmail(
        @Header("Authorization") authHeader: String,
        @Body input: RecipientCreatedByEmail
    ): Single<RecipientAccountInfo>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("v1/transfers")
    fun createTransfer(
        @Header("Authorization") authHeader: String,
        @Body input: CreateTransferRequest
    ): Single<TransferHistory>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("v3/profiles/{profileId}/transfers/{transferId}/payments")
    fun fundTransfer(
        @Header("Authorization") authHeader: String,
        @Path("profileId") profileId: Int,
        @Path("transferId") transferId: Int,
        @Body input: FundTransferTypeRequest
    ): Single<FundTransferTypeResponse>
}