package com.parkee.sendmoney.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatSeekBar
import com.parkee.assets.foundations.BaseFragment
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.TransferReasonFragmentBinding
import com.parkee.sendmoney.navigator.SendMoneyNavigator

class TransferReasonFragment: BaseFragment() {

    private var binding: TransferReasonFragmentBinding? = null
    private val quoteItem by extraNotNull(QUOTE_ITEM, "{}")
    private val recipientId by extraNotNull(RECIPIENT_ID, 0)
    private val profileId by extraNotNull(PROFILE_ID, 0)
    private lateinit var seekBar: AppCompatSeekBar
    private lateinit var button: AppCompatButton
    private val spendingTransferReasonSpinnerItem: MutableList<String> =
        mutableListOf(
            "Select a reason",
            "Sending money home to family",
            "Salary payment"
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TransferReasonFragmentBinding.inflate(inflater, container, false)
        context?.let {
            setupDropdown(it)
        }

        binding?.buttonContinueReason?.setOnClickListener {
            if (binding?.spinnerTransferReasonDropDown?.selectedItem?.toString() == "Select a reason") {
                showShortToast("Please select a reason")
            } else {
                activity?.supportFragmentManager?.let {
                    SendMoneyNavigator.gotoReviewTransferDetailFragment(
                        it,
                        R.id.frameFragmentContainer,
                        quoteItem,
                        recipientId,
                        seekBar,
                        binding?.spinnerTransferReasonDropDown?.selectedItem?.toString(),
                        button,
                        profileId
                    )
                }
            }
        }
        return binding?.root
    }

    private fun setupDropdown(
        context: Context
    ) {
        val transferReasonSpinnerAdapter = object : ArrayAdapter<String>(
            context,
            R.layout.transfer_reason_dropdown_layout,
            R.id.textSpinnerCurrentSelectedReason,
            spendingTransferReasonSpinnerItem
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val checkView: AppCompatImageView = view.findViewById(R.id.imageCheckDropDown)
                if (binding?.spinnerTransferReasonDropDown?.selectedItem?.toString() ==
                    spendingTransferReasonSpinnerItem[position]
                ) {
                    checkView.visibility = View.VISIBLE
                } else checkView.visibility = View.INVISIBLE
                return view
            }
        }
        transferReasonSpinnerAdapter.setDropDownViewResource(R.layout.dropdown_item_layout)
        binding?.spinnerTransferReasonDropDown?.adapter = transferReasonSpinnerAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = TransferReasonFragment::class.java.name
        private const val QUOTE_ITEM = "QUOTE_ITEM"
        private const val RECIPIENT_ID = "RECIPIENT_ID"
        private const val PROFILE_ID = "PROFILE_ID"

        @JvmStatic
        fun newInstance(
            quoteItem: String,
            recipientId: Int,
            seekBar: AppCompatSeekBar,
            button: AppCompatButton,
            profileId: Int
        ) =
            TransferReasonFragment().apply {
                this.seekBar = seekBar
                this.button = button
                arguments = Bundle().apply {
                    putString(QUOTE_ITEM, quoteItem)
                    putInt(RECIPIENT_ID, recipientId)
                    putInt(PROFILE_ID, profileId)
                }
            }
    }
}