package com.parkee.sendmoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parkee.assets.extensions.addToDisposable
import com.parkee.assets.extensions.toCurrencyFormat
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.model.AvailableBalance
import com.parkee.assets.model.BalanceForSpecificCurrency
import com.parkee.assets.model.Quote
import com.parkee.assets.model.QuoteResponseMapResult
import com.parkee.assets.repo.Const
import com.parkee.assets.repo.Repo
import com.parkee.assets.repo.Status
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

interface SendMoneyInternationalViewModel {
    fun setAccountBalance(profileId: Int)
    fun getAccountBalance(): LiveData<BalanceForSpecificCurrency>

    fun setQuote(profileId: Int, sourceAmount: Double)
    fun getQuote(): LiveData<Pair<String, QuoteResponseMapResult?>>
}

class SendMoneyInternationalViewModelImpl: SendMoneyInternationalViewModel, ViewModel() {
    private val data:  MutableLiveData<Pair<String, QuoteResponseMapResult?>> = MutableLiveData()
    private val balanceItem: MutableLiveData<BalanceForSpecificCurrency> = MutableLiveData()

    override fun setQuote(
        profileId: Int,
        sourceAmount: Double
    ) {
        data.postValue(Status.ON_PROGRESS to null)
        Repo.getQuote(profileId.toString(), sourceAmount = sourceAmount)
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

    private fun mapQuote(response: Quote): QuoteResponseMapResult {
        val sourceCurrency = response.source
        val targetCurrency = response.target
        val sourceAmount = response.sourceAmount.toCurrencyFormat()
        val targetAmount = response.targetAmount.toCurrencyFormat()
        val type = if (response.type == "BALANCE_PAYOUT") "Balance transfer" else ""
        val exchangeRate = response.rate.toString()

        val estimateDate = Const.CREATED_DATE_PATTERN.parse(response.deliveryEstimate)
        val deliveryEstimateFormatted = Const.MMMM_D_TH_DATE_PATTERN.format(estimateDate!!)
        val deliveryEstimate = "by $deliveryEstimateFormatted"

        val fee = "${response.fee} $sourceCurrency"
        val subtractResult = "${response.sourceAmount - response.fee} $sourceCurrency"

        return QuoteResponseMapResult(
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
}