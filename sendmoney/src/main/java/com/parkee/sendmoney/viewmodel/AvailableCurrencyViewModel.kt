package com.parkee.sendmoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airbnb.paris.extensions.layoutMarginBottomDp
import com.airbnb.paris.extensions.layoutMarginTopDp
import com.airbnb.paris.extensions.viewGroupStyle
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.model.CurrencyPairs
import com.parkee.assets.model.TargetCurrencies
import com.parkee.assets.repo.CountryNameProvider
import com.parkee.assets.repo.Repo
import com.parkee.assets.viewholder.HeaderSectionItem
import com.parkee.sendmoney.viewholder.AvailableCurrencyItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

interface AvailableCurrencyViewModel {
    fun searchItem(key: String)

    fun setData(currency: String)
    fun getData(): LiveData<List<BaseItem>>
}

class AvailableCurrencyViewModelImpl: AvailableCurrencyViewModel, ViewModel() {
    private val data: MutableLiveData<List<BaseItem>> = MutableLiveData()
    private val item: MutableList<BaseItem> = mutableListOf()
    private val currencyItem: MutableList<BaseItem> = mutableListOf()

    override fun searchItem(key: String) {
        val itemTemp: MutableList<BaseItem> = mutableListOf()
        var check = ""
        if (key.isNotEmpty()) {
            currencyItem.forEach {
                val currency = (it as AvailableCurrencyItem).currency
                val tempCurrency = currency.toLowerCase(Locale.ROOT)
                val tempCountryName = it.countryName?.toLowerCase(Locale.ROOT)
                if (tempCurrency.contains(key.toLowerCase(Locale.ROOT)) ||
                    tempCountryName!!.contains(key.toLowerCase(Locale.ROOT))
                ) {
                    if (check != currency) itemTemp.add(it)
                    check = currency
                }
            }
            if (itemTemp.isNotEmpty()) data.postValue(itemTemp)
        }
        else data.postValue(item)
    }

    override fun setData(currency: String) {
        Repo.getAvailableCurrencyPairs()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                mapCurrencyPairItem(it, currency)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                data.postValue(it)
            }, {
                it.printStackTrace()
            })
    }

    private fun mapCurrencyPairItem(response: CurrencyPairs, currency: String): List<BaseItem> {
        val selectedCurrency = response.sourceCurrencies.firstOrNull { it.currencyCode == currency }
        item.add(
            HeaderSectionItem("Your recent currencies")
                .setTextStyleWithStyleResId(com.parkee.assets.R.style.TextView17_AccountBlue_Regular)
                .setContainerStyle(viewGroupStyle {
                    layoutMarginTopDp(10)
                    layoutMarginBottomDp(10)
                })
        )
        currencyItem.add(AvailableCurrencyItem(currency, CountryNameProvider.getCountryName(currency),true))
        item.addAll(currencyItem.toMutableList())
        item.add(
            HeaderSectionItem("All currencies")
                .setTextStyleWithStyleResId(com.parkee.assets.R.style.TextView17_AccountBlue_Regular)
                .setContainerStyle(viewGroupStyle {
                    layoutMarginTopDp(10)
                    layoutMarginBottomDp(10)
                })
        )
        currencyItem.clear()
        selectedCurrency?.targetCurrencies?.forEach {
            if (it.currencyCode != currency)
                currencyItem.add(
                    AvailableCurrencyItem(
                        it.currencyCode,
                        CountryNameProvider.getCountryName(it.currencyCode)
                    )
                )
        }
        if (currency == "CNY") currencyItem.add(AvailableCurrencyItem("USD", CountryNameProvider.getCountryName("USD")))
        item.addAll(currencyItem)
        return item
    }

    override fun getData(): LiveData<List<BaseItem>> = data
}