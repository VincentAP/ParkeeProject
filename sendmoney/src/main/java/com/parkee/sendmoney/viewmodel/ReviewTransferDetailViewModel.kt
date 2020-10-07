package com.parkee.sendmoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkee.assets.extensions.addToDisposable
import com.parkee.assets.model.RecipientAccountInfo
import com.parkee.assets.model.TransferHistory
import com.parkee.assets.repo.Repo
import com.parkee.assets.repo.Status
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

interface ReviewTransferDetailViewModel {
    fun createTransfer(
        targetAccount: Int,
        quote: Int,
        customerTransactionId: UUID,
        reference: String? = null,
        transferPurpose: String?,
        sourceOfFunds: String
    )
    fun getCreateTransferResult(): LiveData<Pair<String, TransferHistory?>>

    fun setRecipientData(recipientId: Int)
    fun getRecipientData(): LiveData<Pair<String, RecipientAccountInfo?>>
}

class ReviewTransferDetailViewModelImpl: ReviewTransferDetailViewModel, ViewModel() {
    private val data: MutableLiveData<Pair<String, RecipientAccountInfo?>> = MutableLiveData()
    private val transferResult: MutableLiveData<Pair<String, TransferHistory?>> = MutableLiveData()

    override fun createTransfer(
        targetAccount: Int,
        quote: Int,
        customerTransactionId: UUID,
        reference: String?,
        transferPurpose: String?,
        sourceOfFunds: String
    ) {
        transferResult.postValue(Status.ON_PROGRESS to null)
        Repo.createTransfer(
            targetAccount,
            quote,
            customerTransactionId,
            reference,
            transferPurpose,
            sourceOfFunds
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                transferResult.postValue(Status.SUCCESS to it)
            }, {
                it.printStackTrace()
                transferResult.postValue(Status.FAILED to null)
            }).addToDisposable()
    }

    override fun setRecipientData(recipientId: Int) {
        data.postValue(Status.ON_PROGRESS to null)
        Repo.getRecipientAccountInfo(recipientId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                data.postValue(Status.SUCCESS to it)
            }, {
                it.printStackTrace()
                data.postValue(Status.FAILED to null)
            }).addToDisposable()

    }

    override fun getCreateTransferResult(): LiveData<Pair<String, TransferHistory?>> = transferResult

    override fun getRecipientData(): LiveData<Pair<String, RecipientAccountInfo?>> = data
}