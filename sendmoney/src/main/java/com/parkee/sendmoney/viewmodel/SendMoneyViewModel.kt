package com.parkee.sendmoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkee.assets.extensions.addToDisposable
import com.parkee.assets.extensions.toCurrencyFormat
import com.parkee.assets.extensions.toCurrencyFormatWithDecimal
import com.parkee.assets.model.*
import com.parkee.assets.repo.Const
import com.parkee.assets.repo.Repo
import com.parkee.assets.repo.Status
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

interface SendMoneyViewModel {
    fun setAccountBalance(profileId: Int)
    fun getAccountBalance(): LiveData<BalanceForSpecificCurrency>

    fun setQuote(profileId: Int, amount: Double, isFromSameCurrency: Boolean = false)
    fun getQuote(): LiveData<Pair<String, QuoteResponseMapResult?>>

    fun getQuoteResponseMapResult(): List<QuoteResponseMapResult>
}

class SendMoneyViewModelImpl: SendMoneyViewModel, ViewModel() {
    private val data:  MutableLiveData<Pair<String, QuoteResponseMapResult?>> = MutableLiveData()
    private val balanceItem: MutableLiveData<BalanceForSpecificCurrency> = MutableLiveData()
    private var quoteItem: MutableList<QuoteResponseMapResult> = mutableListOf()

    override fun setQuote(
        profileId: Int,
        amount: Double,
        isFromSameCurrency: Boolean
    ) {
        data.postValue(Status.ON_PROGRESS to null)
        if (isFromSameCurrency) {
            Repo.getQuoteTargetAmount(profileId.toString(), targetAmount = amount)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map {
                    mapQuote(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data.postValue(Status.SUCCESS to it)
                }, {
                    it.printStackTrace()
                    data.postValue(Status.FAILED to null)
                }).addToDisposable()
        } else {
            Repo.getQuote(profileId.toString(), sourceAmount = amount)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map {
                    mapQuote(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data.postValue(Status.SUCCESS to it)
                }, {
                    it.printStackTrace()
                    data.postValue(Status.FAILED to null)
                }).addToDisposable()
        }
    }

    private fun mapQuote(
        response: Quote
    ): QuoteResponseMapResult {
        val sourceCurrency = response.source
        val targetCurrency = response.target
        val sourceAmount = response.sourceAmount.toCurrencyFormatWithDecimal()
        val targetAmount = response.targetAmount.toCurrencyFormatWithDecimal()
        val type = if (response.type == "BALANCE_PAYOUT") "Balance transfer" else ""
        val exchangeRate = response.rate.toString()

        val estimateDate = Const.CREATED_DATE_PATTERN.parse(response.deliveryEstimate)
        val deliveryEstimateFormatted = Const.MMMM_D_TH_DATE_PATTERN.format(estimateDate!!)
        val deliveryEstimate = "by $deliveryEstimateFormatted"

        val fee = "${response.fee} $sourceCurrency"
        val subtractResult = "${response.sourceAmount - response.fee} $sourceCurrency"

        val itemResult = QuoteResponseMapResult(
            response.id,
            sourceCurrency,
            targetCurrency,
            sourceAmount,
            targetAmount,
            type,
            subtractResult,
            exchangeRate,
            deliveryEstimate,
            fee
        )
        quoteItem.clear()
        quoteItem.add(itemResult)
        return itemResult
    }

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

    private fun mapAvailableBalanceItem(response: List<AvailableBalance>): BalanceForSpecificCurrency {
        var item = BalanceForSpecificCurrency(999.000)
        for (i in response.indices) {
            response[i].balances.firstOrNull {
                it.currency == "GBP"
            }?.let {
                item = BalanceForSpecificCurrency(it.amount.value)
            }
            break
        }
        return item
    }

    override fun getAccountBalance(): LiveData<BalanceForSpecificCurrency> = balanceItem

    override fun getQuote(): LiveData<Pair<String, QuoteResponseMapResult?>> = data

    override fun getQuoteResponseMapResult(): List<QuoteResponseMapResult> = quoteItem
}