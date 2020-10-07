package com.parkee.sendmoney.navigator

import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.fragment.app.FragmentManager
import com.parkee.assets.foundations.BaseNavigator
import com.parkee.sendmoney.view.ChooseTransferTypeFragment
import com.parkee.sendmoney.view.CreateRecipientFragment
import com.parkee.sendmoney.view.ReviewTransferDetailFragment
import com.parkee.sendmoney.view.TransferReasonFragment

object SendMoneyNavigator: BaseNavigator() {
    fun gotoCreateRecipientFragment(
        fragmentManager: FragmentManager,
        container: Int,
        item: String,
        quoteItem: String?,
        seekBar: AppCompatSeekBar,
        button: AppCompatButton
    ) {
        CreateRecipientFragment().setSeekBar(seekBar)
        navigateToFragment(
            fragmentManager,
            container,
            CreateRecipientFragment.newInstance(
                item,
                seekBar,
                quoteItem,
                button
            ),
            CreateRecipientFragment.TAG
        )
    }

    fun gotoCreateTransferReasonFragment(
        fragmentManager: FragmentManager,
        container: Int,
        quoteItem: String,
        recipientId: Int,
        seekBar: AppCompatSeekBar,
        button: AppCompatButton,
        profileId: Int
    ) {
        navigateToFragment(
            fragmentManager,
            container,
            TransferReasonFragment.newInstance(
                quoteItem,
                recipientId,
                seekBar,
                button,
                profileId
            ),
            TransferReasonFragment.TAG
        )
    }

    fun gotoReviewTransferDetailFragment(
        fragmentManager: FragmentManager,
        container: Int,
        quoteItem: String,
        recipientId: Int,
        seekBar: AppCompatSeekBar,
        transferPurpose: String?,
        button: AppCompatButton,
        profileId: Int
    ) {
        navigateToFragment(
            fragmentManager,
            container,
            ReviewTransferDetailFragment.newInstance(
                quoteItem,
                recipientId,
                seekBar,
                transferPurpose,
                button,
                profileId
            ),
            ReviewTransferDetailFragment.TAG
        )
    }

    fun gotoChooseTransferTypeFragment(
        fragmentManager: FragmentManager,
        container: Int,
        quoteItem: String,
        button: AppCompatButton,
        seekBar: AppCompatSeekBar,
        transferId: Int,
        profileId: Int
    ) {
        navigateToFragment(
            fragmentManager,
            container,
            ChooseTransferTypeFragment.newInstance(
                quoteItem,
                button,
                seekBar,
                transferId,
                profileId
            ),
            ChooseTransferTypeFragment.TAG
        )
    }
}