package com.parkee.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkee.assets.extensions.addToDisposable
import com.parkee.assets.model.User
import com.parkee.assets.repo.AuthenticationInterceptor
import com.parkee.assets.repo.Const
import com.parkee.assets.repo.Repo
import com.parkee.assets.repo.Status
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

interface LoginViewModel {
    fun requestLogin()
    fun getLoginResponse(): LiveData<Pair<String, List<User>?>>
}

class LoginViewModelImpl: LoginViewModel, ViewModel() {
    private val loginResponse: MutableLiveData<Pair<String, List<User>?>> = MutableLiveData()

    override fun requestLogin() {
        loginResponse.postValue(Status.ON_PROGRESS to null)
        Repo.login()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loginResponse.postValue(Status.SUCCESS to it)
            }, {
                it.printStackTrace()
                loginResponse.postValue(Status.FAILED to null)
            })
            .addToDisposable()
    }

    override fun getLoginResponse(): LiveData<Pair<String, List<User>?>> = loginResponse
}