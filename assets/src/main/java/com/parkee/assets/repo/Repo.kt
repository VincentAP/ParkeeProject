package com.parkee.assets.repo

import com.parkee.assets.model.User
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

object Repo {
    internal var disposable: CompositeDisposable? = null

    fun login(): Single<List<User>> {
        return AuthenticationInterceptor()
            .getService(Const.LOGIN_BASE_URL)
            .login(Const.LOGIN_HEADER_ATTR)
    }
}