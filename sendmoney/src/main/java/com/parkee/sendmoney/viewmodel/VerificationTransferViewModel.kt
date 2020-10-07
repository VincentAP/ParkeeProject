package com.parkee.sendmoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkee.assets.extensions.addToDisposable
import com.parkee.assets.model.FundTransferTypeResponse
import com.parkee.assets.repo.Repo
import com.parkee.assets.repo.Status
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

interface VerificationTransferViewModel {
    fun createFundTransfer(
        profileId: Int,
        transferId: Int
    )

    fun getFundTransferResult(): LiveData<Pair<String, FundTransferTypeResponse?>>
}

class VerificationTransferViewModelImpl: VerificationTransferViewModel, ViewModel() {
    private val data: MutableLiveData<Pair<String, FundTransferTypeResponse?>> = MutableLiveData()

    override fun createFundTransfer(profileId: Int, transferId: Int) {
        data.postValue(Status.ON_PROGRESS to null)
        Repo.fundTransfer(
            profileId = profileId,
            transferId = transferId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                data.postValue(Status.SUCCESS to it)
            }, {
                it.printStackTrace()
                data.postValue(Status.FAILED to null)
            }).addToDisposable()
    }

    override fun getFundTransferResult(): LiveData<Pair<String, FundTransferTypeResponse?>> = data
}