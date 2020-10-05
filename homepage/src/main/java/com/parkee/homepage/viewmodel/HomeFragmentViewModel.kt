package com.parkee.homepage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airbnb.paris.extensions.layoutMarginBottomDp
import com.airbnb.paris.extensions.textColorRes
import com.airbnb.paris.extensions.textViewStyle
import com.airbnb.paris.extensions.viewGroupStyle
import com.parkee.assets.extensions.addToDisposable
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.model.*
import com.parkee.assets.repo.Const
import com.parkee.assets.repo.Repo
import com.parkee.assets.repo.Status
import com.parkee.assets.viewholder.HeaderSectionItem
import com.parkee.homepage.viewholder.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

interface HomeFragmentViewModel {
    fun setAccountBalance(profileId: Int)
    fun getAccountBalance(): LiveData<List<BaseItem>>

    fun setHistory(profileId: Int)
    fun getHistory(): LiveData<Pair<String, List<BaseItem>?>>
}

class HomeFragmentViewModelImpl: HomeFragmentViewModel, ViewModel() {
    private val historyItem: MutableLiveData<Pair<String, List<BaseItem>?>> = MutableLiveData()
    private val balanceItem: MutableLiveData<List<BaseItem>> = MutableLiveData()

    override fun setAccountBalance(profileId: Int) {
        Repo.getAccountBalance(profileId)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                mapAvailableBalanceItem(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                balanceItem.postValue(it)
            }, {
                it.printStackTrace()
            }).addToDisposable()
    }

    private fun mapAvailableBalanceItem(response: List<AvailableBalance>): List<AvailableBalanceItem> {
        val availableBalanceItem: MutableList<AvailableBalanceItem> = mutableListOf()
        response.forEach { resp ->
            val size = resp.balances.size
            resp.balances.forEachIndexed { index, balance ->
                val isLastItem = index == size-1
                availableBalanceItem.add(
                    AvailableBalanceItem(
                        balance.amount.value,
                        balance.amount.currency,
                        isLastItem
                    )
                )
            }
        }
        return availableBalanceItem
    }

    override fun setHistory(profileId: Int) {
        val createdDateStartCalendar = Calendar.getInstance()
        createdDateStartCalendar.set(Calendar.DAY_OF_MONTH, 1)
        createdDateStartCalendar.set(Calendar.MONTH, 10)
        createdDateStartCalendar.set(Calendar.YEAR, 2020)

        val createdDateEndCalendar = Calendar.getInstance()
        createdDateEndCalendar.set(Calendar.DAY_OF_MONTH, 31)
        createdDateEndCalendar.set(Calendar.MONTH, 10)
        createdDateEndCalendar.set(Calendar.YEAR, 2020)

        historyItem.postValue(Status.ON_PROGRESS to listOf(TransferHistoryShimmerItem()))
        Repo.getTransferHistory(
            0,
            100,
            profileId,
            "incoming_payment_waiting, waiting_recipient_input_to_proceed, processing, funds_converted, outgoing_payment_sent, cancelled, bounced_back",
            "2020-10-1",
            "2020-10-31"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                mapTransferHistory(it)
            }
            .subscribe({
                getTransferDeliveryTime(it)
            }, {
                it.printStackTrace()
            }).addToDisposable()
    }

    private fun getTransferDeliveryTime(response: List<TransferHistoryWrapper>) {
        Observable.fromIterable(response)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { item ->
                Repo.getDeliveryTime(item.transferId)
                    .observeOn(Schedulers.computation())
                    .subscribe ({ dt ->
                        item.estimatedDeliveryDate = dt.estimatedDeliveryDate
                    }, {
                        it.printStackTrace()
                    }).addToDisposable()
                Observable.just(item)
            }
            .toList()
            .subscribe({
                getRecipientName(it)
            }, {
                it.printStackTrace()
            }).addToDisposable()
    }

    private fun getRecipientName(response: List<TransferHistoryWrapper>) {
        Observable.fromIterable(response)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { item ->
                Repo.getRecipientAccountInfo(item.targetAccount)
                    .observeOn(Schedulers.computation())
                    .subscribe ({ an ->
                        item.accountHolderName = an.accountHolderName
                    }, {
                        it.printStackTrace()
                    }).addToDisposable()
                Observable.just(item)
            }
            .toList()
            .map {
                mapTransferHistoryItem(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty()) {
                    historyItem.postValue(Status.SUCCESS to it)
                } else {
                    historyItem.postValue(Status.SUCCEED_BUT_EMPTY to listOf(EmptyTransactionItem()))
                }
            }, {
                it.printStackTrace()
                historyItem.postValue(Status.FAILED to null)
            }).addToDisposable()
    }

    private fun getTransferStatus(response: TransferHistoryWrapper): String {
        when(response.status) {
            "incoming_payment_waiting" -> return "Waiting for you to pay"
            "waiting_recipient_input_to_proceed" -> return "Waiting for recipient to enter data"
            "processing" -> {
                val date = Const.DD_MMMM_YYYY_DATE_PATTERN.parse(response.estimatedDeliveryDate!!)
                val dateInText = Const.DD_MMMM_YYYY_DATE_PATTERN.format(date!!)
                return "Arriving, $dateInText"
            }
            "funds_converted" -> return "Funds have been converted"
            "outgoing_payment_sent" -> return "Completed"
            "cancelled" -> {
                val cancelledDate = Const.YYYY_MM_DD_HH_MM_SS_DATE_PATTERN.parse(response.createdDate)
                val cancelledDateInText = Const.DD_MMMM_YYYY_DATE_PATTERN.format(cancelledDate!!)
                return "Cancelled, $cancelledDateInText"
            }
            "bounced_back" -> return "Arriving later"
        }
        return ""
    }

    private fun sortItem(response: List<TransferHistoryWrapper>): List<TransferHistoryWrapper> {
        response.forEach { item1 ->
            item1.createdDateInDate =
                Const.YYYY_MM_DD_HH_MM_SS_DATE_PATTERN.parse(item1.createdDate)
        }
        return response.sortedByDescending {
            it.createdDateInDate
        }
    }

    private fun mapTransferHistoryItem(response: List<TransferHistoryWrapper>): List<BaseItem> {
        val item: MutableList<BaseItem> = mutableListOf()
        val responseCompleted: MutableList<TransferHistoryWrapper> = mutableListOf()
        val responseCancelled: MutableList<TransferHistoryWrapper> = mutableListOf()
        val responseWaiting: MutableList<TransferHistoryWrapper> = mutableListOf()

        if (response.isNotEmpty()) {
            val sortedResponse = sortItem(response)
            sortedResponse.forEach { item2 ->
                when(item2.status) {
                    "outgoing_payment_sent" -> responseCompleted.add(item2)
                    "cancelled" -> responseCancelled.add(item2)
                    else -> responseWaiting.add(item2)
                }
            }

            if (responseWaiting.isNotEmpty()) {
                item.add(
                    HeaderSectionItem("In progress")
                        .setTextStyleWithStyleResId(com.parkee.assets.R.style.TextView17_LighterGrey_Bold)
                        .setContainerStyle(viewGroupStyle {
                            layoutMarginBottomDp(20)
                        })
                )
                responseWaiting.forEach {
                    var currencyFrom = "${it.sourceValue} ${it.sourceCurrency}"
                    var isCurrencyFromHasValue = true
                    if (it.sourceValue == 0.0) {
                        currencyFrom = "${it.sourceCurrency} to"
                        isCurrencyFromHasValue = false
                    }
                    item.add(
                        TransferHistoryItem(
                            it.accountHolderName,
                            getTransferStatus(it),
                            currencyFrom,
                            "${it.targetValue} ${it.targetCurrency}",
                            isCurrencyFromHasValue = isCurrencyFromHasValue
                        )
                    )
                }
            }

            if (responseCompleted.isNotEmpty()) {
                item.add(
                    HeaderSectionItem("In progress")
                        .setTextStyleWithStyleResId(com.parkee.assets.R.style.TextView17_LighterGrey_Bold)
                        .setContainerStyle(viewGroupStyle {
                            layoutMarginBottomDp(20)
                        })
                )
                responseCompleted.forEach {
                    item.add(
                        TransferHistoryItem(
                            it.accountHolderName,
                            getTransferStatus(it),
                            "${it.sourceValue} ${it.sourceCurrency}",
                            "${it.targetValue} ${it.targetCurrency}"
                        )
                    )
                }
            }

            if (responseCancelled.isNotEmpty()) {
                item.add(
                    HeaderSectionItem("History")
                        .setTextStyleWithStyleResId(com.parkee.assets.R.style.TextView17_LighterGrey_Bold)
                        .setContainerStyle(viewGroupStyle {
                            layoutMarginBottomDp(20)
                        })
                )
                responseCancelled.forEach {
                    item.add(
                        TransferHistoryItem(
                            it.accountHolderName,
                            getTransferStatus(it),
                            "${it.sourceValue} ${it.sourceCurrency}",
                            "${it.targetValue} ${it.targetCurrency}",
                            true
                        )
                    )
                }

                val total = responseWaiting.size + responseCompleted.size + responseCancelled.size
                item.add(TransferHistoryTotalItem(total))
            }
        }
        return item
    }

    private fun mapTransferHistory(response: List<TransferHistory>): List<TransferHistoryWrapper> {
        val transferHistoryItem: MutableList<TransferHistoryWrapper> = mutableListOf()
        response.forEach {
            transferHistoryItem.add(
                TransferHistoryWrapper(
                    it.id,
                    it.targetAccount,
                    it.status,
                    it.sourceValue,
                    it.sourceCurrency,
                    it.targetValue,
                    it.targetCurrency,
                    it.created
                )
            )
        }
        return transferHistoryItem
    }

    override fun getAccountBalance(): LiveData<List<BaseItem>> = balanceItem

    override fun getHistory(): LiveData<Pair<String, List<BaseItem>?>> = historyItem
}