package com.parkee.sendmoney.view

import android.content.Context
import android.os.Bundle
import android.widget.AdapterView.OnItemSelectedListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.parkee.assets.extensions.animate
import com.parkee.assets.extensions.animateReverse
import com.parkee.assets.extensions.fromJsonToList
import com.parkee.assets.foundations.BaseFragment
import com.parkee.assets.model.QuoteResponseMapResult
import com.parkee.assets.model.RecipientAccountInfo
import com.parkee.assets.repo.PurposeProvider
import com.parkee.assets.repo.Status
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.ReviewTransferDetailFragmentBinding
import com.parkee.sendmoney.navigator.SendMoneyNavigator
import com.parkee.sendmoney.viewmodel.ReviewTransferDetailViewModelImpl
import com.tooltip.Tooltip
import java.util.*

class ReviewTransferDetailFragment: BaseFragment() {

    private var binding: ReviewTransferDetailFragmentBinding? = null
    private lateinit var reviewTransferDetailViewModel: ReviewTransferDetailViewModelImpl
    private val quoteItem by extraNotNull(QUOTE_ITEM, "{}")
    private val recipientId by extraNotNull(RECIPIENT_ID, 0)
    private val profileId by extraNotNull(PROFILE_ID, 0)
    private val transferPurpose by extraNotNull(TRANSFER_PURPOSE, "")
    private lateinit var itemTooltip: String
    private var isTooltipShowed = false
    private lateinit var seekBar: AppCompatSeekBar
    private lateinit var button: AppCompatButton
    private var isBeginningOfSystem = true
    private var isTransferCreated = false
    private val spinnerItem: MutableList<String> =
        mutableListOf(
            "Family",
            "Bills",
            "Studies",
            "Savings",
            "Friends",
            "Travel",
            "Relocation",
            "Property",
            "Other",
            "None"
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ReviewTransferDetailFragmentBinding.inflate(inflater, container, false)
        button.visibility = View.GONE
        seekBar.animate(50, 75)
        context?.let {
            setupDropdown(it)
        }
        binding?.textGuaranteedRate?.setOnClickListener {
            if (!isTooltipShowed) showTooltip()
            else {
                isTooltipShowed = false
                binding?.layoutTooltip?.root?.visibility = View.GONE
            }
        }

        binding?.buttonContinue?.setOnClickListener {
            if (!isTransferCreated) {
                isTransferCreated = true
                val itemList = quoteItem.fromJsonToList<QuoteResponseMapResult>()
                binding?.progressIndicator?.visibility = View.VISIBLE
                binding?.buttonContinue?.isEnabled = false
                itemList?.get(0)?.let { quoteResp ->
                    reviewTransferDetailViewModel.createTransfer(
                        recipientId,
                        quoteResp.id,
                        UUID.randomUUID(),
                        binding?.layoutReferences?.editReferences?.text?.toString(),
                        PurposeProvider.getTransferPurpose(transferPurpose),
                        "verification.source.of.funds.other"
                    )
                }
            }
        }
        setupReviewTransferDetailViewModel()
        return binding?.root
    }

    private fun showTooltip() {
        isTooltipShowed = true
        binding?.layoutTooltip?.apply {
            root.visibility = View.VISIBLE
            textTooltip.text = HtmlCompat.fromHtml(
                resources.getString(R.string.guaranteed_tooltip, itemTooltip),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
    }

    private fun setupReviewTransferDetailViewModel() {
        reviewTransferDetailViewModel = ReviewTransferDetailViewModelImpl()
        reviewTransferDetailViewModel.getRecipientData().observe(this, Observer { (status, item) ->
            when(status) {
                Status.ON_PROGRESS -> {
                    binding?.coordinatorProgressBar?.visibility = View.VISIBLE
                    binding?.scrollTransferReviewWrapper?.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding?.coordinatorProgressBar?.visibility = View.GONE
                    binding?.scrollTransferReviewWrapper?.visibility = View.VISIBLE
                    initView(item)
                }
                Status.FAILED -> {
                    showShortToast("Failed")
                }
            }
        })

        reviewTransferDetailViewModel.getCreateTransferResult().observe(this, Observer { (status, item) ->
            when(status) {
                Status.ON_PROGRESS -> {
                    binding?.apply {
                        context?.let { cont ->
                            relativeButtonWrapper.setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseGrey61
                                )
                            )
                        }
                    }
                }
                Status.SUCCESS -> {
                    isTransferCreated = false
                    binding?.apply {
                        buttonContinue.isEnabled = true
                        context?.let { cont ->
                            relativeButtonWrapper.setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseBlue
                                )
                            )
                        }
                        progressIndicator.visibility = View.GONE
                    }
                    seekBar.animate(75, 100)
                    activity?.supportFragmentManager?.let {
                        SendMoneyNavigator.gotoChooseTransferTypeFragment(
                            it,
                            R.id.frameFragmentContainer,
                            quoteItem,
                            button,
                            seekBar,
                            item!!.id,
                            profileId
                        )
                    }
                }
                Status.FAILED -> {
                    binding?.apply {
                        buttonContinue.isEnabled = true
                        context?.let { cont ->
                            relativeButtonWrapper.setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseBlue
                                )
                            )
                        }
                        progressIndicator.visibility = View.GONE
                    }
                    showShortToast("Failed")
                }
            }
        })
    }

    private fun initView(recipientItem: RecipientAccountInfo?) {
        val itemList = quoteItem.fromJsonToList<QuoteResponseMapResult>()
        binding?.apply {
            itemList?.get(0)?.let {
                recipientItem?.let { recipient ->
                    val totalSend = "${it.sourceAmount} ${it.sourceCurrency}"
                    val totalFee = "${it.fee} ${it.sourceCurrency}"
                    val convertValue = "${it.subtractResult} ${it.sourceCurrency}"
                    val targetAmount = "${it.targetAmount} ${it.targetCurrency}"

                    val temp = recipient.accountHolderName
                    val firstName = temp.split(" ".toRegex(), 2).toTypedArray()
                    val targetPersonName = firstName[0]

                    itemTooltip = totalSend
                    textTotalSend.text = totalSend
                    textTotalFeeValue.text = totalFee
                    textConvertValue.text = convertValue
                    textGuaranteedRateValue.text = it.exchangeRate
                    textPersonGets.text = resources.getString(R.string.person_gets, targetPersonName)
                    textPersonGetsValue.text = targetAmount
                    textShouldArriveValue.text = it.deliveryEstimate
                    textNameValue.text = recipient.accountHolderName
                    if (recipient.details.email == null) {
                        relativeTransferDetails9.visibility = View.GONE
                    } else textEmailValue.text = recipient.details.email
                    textAccountNumber.text = recipient.details.accountNumber
                    textReferenceFor.text = resources.getString(R.string.references_for, targetPersonName)
                    layoutReferences.editReferences.hint = resources.getString(R.string.reference_message, targetPersonName)
                }
            }
        }
    }

    private fun setupDropdown(
        context: Context
    ) {
        val spinnerAdapter = object : ArrayAdapter<String>(
            context,
            R.layout.reference_dropdown_layout,
            R.id.textEmo,
            spinnerItem
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textItem: TextView = view.findViewById(R.id.textSuggestion)
                textItem.visibility = if (position == 0)  View.VISIBLE else View.GONE
                return view
            }
        }
        spinnerAdapter.setDropDownViewResource(R.layout.reference_dropdown_item_layout)
        binding?.layoutReferences?.spinnerTransferReasonDropDown?.adapter =
            spinnerAdapter
        binding?.layoutReferences?.spinnerTransferReasonDropDown?.onItemSelectedListener =
            object : OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (!isBeginningOfSystem) {
                        binding?.layoutReferences?.editReferences?.setText(
                            binding?.layoutReferences?.spinnerTransferReasonDropDown?.selectedItem?.toString()
                        )
                    }
                    isBeginningOfSystem = false
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
    }

    override fun onBackPressed(): Boolean {
        seekBar.animateReverse(75, 50)
        return super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        reviewTransferDetailViewModel.setRecipientData(recipientId)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = ReviewTransferDetailFragment::class.java.name
        private const val QUOTE_ITEM = "QUOTE_ITEM"
        private const val RECIPIENT_ID = "RECIPIENT_ID"
        private const val PROFILE_ID = "PROFILE_ID"
        private const val TRANSFER_PURPOSE = "TRANSFER_PURPOSE"

        @JvmStatic
        fun newInstance(
            quoteItem: String,
            recipientId: Int,
            seekBar: AppCompatSeekBar,
            transferPurpose: String?,
            button: AppCompatButton,
            profileId: Int
        ) = ReviewTransferDetailFragment().apply {
            this.seekBar = seekBar
            this.button = button
            arguments = Bundle().apply {
                putString(QUOTE_ITEM, quoteItem)
                putInt(RECIPIENT_ID, recipientId)
                putString(TRANSFER_PURPOSE, transferPurpose)
                putInt(PROFILE_ID, profileId)
            }
        }
    }
}