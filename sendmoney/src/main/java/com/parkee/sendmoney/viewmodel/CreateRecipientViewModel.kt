package com.parkee.sendmoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airbnb.paris.extensions.layoutMarginBottomDp
import com.airbnb.paris.extensions.layoutMarginTopDp
import com.airbnb.paris.extensions.viewGroupStyle
import com.parkee.assets.extensions.addToDisposable
import com.parkee.assets.extensions.applyLastItemDecoration
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.model.RecipientAccountInfo
import com.parkee.assets.repo.Repo
import com.parkee.assets.repo.Status
import com.parkee.assets.viewholder.HeaderSectionItem
import com.parkee.assets.viewholder.LineDividerItem
import com.parkee.sendmoney.viewholder.AdditionalButtonItem
import com.parkee.sendmoney.viewholder.BlueTextItem
import com.parkee.sendmoney.viewholder.CreateRecipientItem
import com.parkee.sendmoney.viewholder.RecipientDetailItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

interface CreateRecipientViewModel {
    fun setData(
        profile: Int,
        currency: String,
        userInitial: String
    )
    fun setCreatedRecipientDetail(
        id: Int,
        accountHolderName: String,
        email: String?,
        aliPayNumber: String
    )

    fun getData(): LiveData<Pair<String, List<BaseItem>?>>
}

class CreateRecipientViewModelImpl: CreateRecipientViewModel, ViewModel() {
    private val data: MutableLiveData<Pair<String, List<BaseItem>?>> = MutableLiveData()

    override fun setData(
        profile: Int,
        currency: String,
        userInitial: String
    ) {
        data.postValue(Status.ON_PROGRESS to null)
        Repo.getAvailableRecipient(profile, currency)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                mapRecipientAccount(it, userInitial)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                data.postValue(Status.SUCCESS to it)
            }, {
                it.printStackTrace()
                data.postValue(Status.FAILED to null)
            }).addToDisposable()
    }

    private fun getInitial(name: String): String {
        val temp = name.split(" ".toRegex()).toTypedArray()
        var initial = ""
        for (i in temp.indices) {
            initial += temp[i][0]
        }
        return initial.toUpperCase(Locale.ROOT)
    }

    private fun mapRecipientAccount(
        response: List<RecipientAccountInfo>,
        userInitial: String
    ): List<BaseItem> {
        val item: MutableList<BaseItem> = mutableListOf()
        item.add(
            HeaderSectionItem("Existing recipients")
                .setTextStyleWithStyleResId(
                    com.parkee.assets.R.style.TextView17_AccountBlue_SemiBold
                )
        )
        item.add(LineDividerItem())
        response.forEach {
            val email = if (it.details.email == null) null else it.details.email
            val endingEmailIdx = if (it.details.email == null) null
            else if (it.details.email!!.length > 4) it.details.email!!.length-4
            else 0
            val endingAccountNumber = if (it.details.accountNumber.length > 4) it.details.accountNumber.length-4
            else 0
            val endingEmail = if (endingEmailIdx == null) it.details.accountNumber.substring(endingAccountNumber)
            else it.details.email!!.substring(endingEmailIdx)
            val detail = "${it.currency} account ending with $endingEmail"
            item.add(
                CreateRecipientItem(
                    it.id,
                    getInitial(it.accountHolderName),
                    it.accountHolderName,
                    detail,
                    email,
                    it.details.accountNumber
                )
            )
        }
        item.add(
            HeaderSectionItem("New recipient")
                .setTextStyleWithStyleResId(
                    com.parkee.assets.R.style.TextView17_AccountBlue_SemiBold
                )
                .setContainerStyle(viewGroupStyle {
                    layoutMarginTopDp(20)
                })
        )
        item.add(LineDividerItem())
        item.add(
            CreateRecipientItem(
                -1,
                userInitial,
                "Myself"
            )
        )
        item.add(
            CreateRecipientItem(
                -1,
                "SE",
                "Someone else"
            )
        )
        item.add(
            CreateRecipientItem(
                -1,
                "BoC",
                "Business or charity"
            )
        )
        item.add(BlueTextItem("Transfer Wise sends money to bank accounts. Looking to send money another way?"))
        item.applyLastItemDecoration()
        return item
    }

    override fun setCreatedRecipientDetail(
        id: Int,
        accountHolderName: String,
        email: String?,
        aliPayNumber: String
    ) {
        val item: MutableList<BaseItem> = mutableListOf()
        item.add(
            RecipientDetailItem (
                id,
                getInitial(accountHolderName),
                accountHolderName,
                email,
                aliPayNumber
            )
        )
        item.add(
            BlueTextItem(
                "Select another recipient",
                "center"
            )
        )
        item.add(AdditionalButtonItem())
        data.postValue(Status.SUCCESS to item)
    }

    override fun getData(): LiveData<Pair<String, List<BaseItem>?>> = data
}