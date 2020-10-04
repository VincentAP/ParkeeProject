package com.parkee.assets.repo

import com.parkee.assets.model.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

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
}