package com.parkee.sendmoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkee.assets.extensions.addToDisposable
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.model.CreateRecipientResponse
import com.parkee.assets.model.RecipientAccountInfo
import com.parkee.assets.repo.Repo
import com.parkee.assets.repo.Status
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

interface CreateNewRecipientViewModel {
    fun setData(
        profileId: Int,
        accountHolderName: String,
        currency: String,
        type: String,
        legalType: String? = null,
        cardNumber: String? = null,
        isByEmail: Boolean = false,
        email: String? = null
    )
    fun getData(): LiveData<Pair<String, CreateRecipientResponse?>>
    fun getDataByEmail(): LiveData<Pair<String, RecipientAccountInfo?>>
}

class CreateNewRecipientViewModelImpl: CreateNewRecipientViewModel, ViewModel() {
    private val data: MutableLiveData<Pair<String, CreateRecipientResponse?>> = MutableLiveData()
    private val dataByEmail: MutableLiveData<Pair<String, RecipientAccountInfo?>> = MutableLiveData()

    override fun setData(
        profileId: Int,
        accountHolderName: String,
        currency: String,
        type: String,
        legalType: String?,
        cardNumber: String?,
        isByEmail: Boolean,
        email: String?
    ) {
        dataByEmail.postValue(Status.ON_PROGRESS to null)
        if (!isByEmail) {
            Repo.createRecipient(
                profileId,
                accountHolderName,
                currency,
                type,
                legalType!!,
                cardNumber!!
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data.postValue(Status.SUCCESS to it)
                }, {
                    it.printStackTrace()
                    data.postValue(Status.FAILED to null)
                }).addToDisposable()
        } else {
            Repo.createRecipientByEmail(
                profileId,
                accountHolderName,
                currency,
                type,
                email!!
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataByEmail.postValue(Status.SUCCESS to it)
                }, {
                    it.printStackTrace()
                    dataByEmail.postValue(Status.FAILED to null)
                }).addToDisposable()
        }
    }

    override fun getData(): LiveData<Pair<String, CreateRecipientResponse?>> = data

    override fun getDataByEmail(): LiveData<Pair<String, RecipientAccountInfo?>> = dataByEmail
}