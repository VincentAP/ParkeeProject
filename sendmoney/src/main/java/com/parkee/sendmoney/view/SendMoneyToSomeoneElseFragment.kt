package com.parkee.sendmoney.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.parkee.assets.extensions.fromJsonToList
import com.parkee.assets.foundations.BaseDialogFragment
import com.parkee.assets.model.User
import com.parkee.assets.repo.Status
import com.parkee.sendmoney.databinding.SendMoneyToRecipientFragmentBinding
import com.parkee.sendmoney.viewmodel.CreateNewRecipientViewModelImpl

class SendMoneyToSomeoneElseFragment: BaseDialogFragment() {

    private var binding: SendMoneyToRecipientFragmentBinding? = null
    private lateinit var createNewRecipientViewModel: CreateNewRecipientViewModelImpl
    private var onRecipientCreated: OnRecipientCreated? = null
    private var selectedTabBg: Int? = null
    private var unselectedTabBg: Int? = null
    private var isUnionPaySelected = true
    private val item by extraNotNull(ITEM, "{}")
    private var isRequest = false

    fun setOnRecipientCreated(onRecipientCreated: OnRecipientCreated) = apply {
        this.onRecipientCreated = onRecipientCreated
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SendMoneyToRecipientFragmentBinding.inflate(inflater, container, false)
        context?.let {
            selectedTabBg = ContextCompat.getColor(it, com.parkee.assets.R.color.transferWiseBlue)
            unselectedTabBg = ContextCompat.getColor(it, com.parkee.assets.R.color.transferWiseAccountBlue)
        }

        binding?.apply {
            imageSomeoneClose.setOnClickListener { dismiss() }

            linearUnionPayWrapper.setOnClickListener {
                isUnionPaySelected = true
                textPaymentType.text = "UnionPay card number"
                layoutAlipayMessage.root.visibility = View.GONE
                viewUnionPay.visibility = View.VISIBLE
                editPaymentTypeNumber.hint = "6240008631401148"
                selectedTabBg?.let { it1 ->
                    textUnionPay.setTextColor(it1)
                    viewUnionPay.setBackgroundColor(it1)
                }
                viewAlipay.visibility = View.GONE
                unselectedTabBg?.let { it2 ->
                    textAlipay.setTextColor(it2)
                }
            }
            linearAlipayWrapper.setOnClickListener {
                isUnionPaySelected = false
                textPaymentType.text = "Alipay ID (mobile number or email)"
                layoutAlipayMessage.root.visibility = View.VISIBLE
                viewAlipay.visibility = View.VISIBLE
                editPaymentTypeNumber.hint = "+8612345678901 or a@a.com"
                    selectedTabBg?.let { it1 ->
                    textAlipay.setTextColor(it1)
                    viewAlipay.setBackgroundColor(it1)
                }
                viewUnionPay.visibility = View.GONE
                unselectedTabBg?.let { it2 ->
                    textUnionPay.setTextColor(it2)
                }
            }

            buttonConfirm.setOnClickListener {
                if (isUnionPaySelected) {
                    showShortToast("Currently we only provide Alipay")
                } else if (editFullName.text.toString().isEmpty()) {
                    showShortToast("Please enter recipient name")
                } else if (editFullName.text.toString().isNotEmpty()
                    && !editFullName.text.toString().contains(" ")) {
                    showShortToast("Please enter recipient full name")
                } else if (editFullName.text.toString().isNotEmpty()
                    && editFullName.text.toString().contains(" ")
                    && editPaymentTypeNumber.text.toString().isEmpty()) {
                    showShortToast("Please fill out Alipay ID")
                } else if (editFullName.text.toString().isNotEmpty()
                    && editFullName.text.toString().contains(" ")
                    && editPaymentTypeNumber.text.toString().isNotEmpty()) {
                    if (editEmail.text.toString().isNotEmpty()
                        && !editEmail.text.toString().contains('@')) {
                        showShortToast("Please input right email")
                    } else if (editEmail.text.toString().isNotEmpty()
                        && editEmail.text.toString().contains('@')) {
                        refreshPage(
                            editFullName.text.toString(),
                            "GBP",
                            "email",
                            isByEmail = true,
                            email = editEmail.text.toString()
                        )
                    } else {
                        refreshPage(
                            editFullName.text.toString(),
                            "CNY",
                            "chinese_alipay",
                            "PRIVATE",
                            editPaymentTypeNumber.text.toString(),
                            email = editEmail.text.toString()
                        )
                    }
                }
            }
        }

        setupCreateNewRecipientViewModel()
        return binding?.root
    }

    private fun setupCreateNewRecipientViewModel() {
        createNewRecipientViewModel = CreateNewRecipientViewModelImpl()
        createNewRecipientViewModel.getData().observe(this, Observer { (status, item) ->
            when(status) {
                Status.ON_PROGRESS -> {
                    binding?.relativeSendMoneyButtonWrapper?.apply {
                        context?.let { cont ->
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseGrey61
                                )
                            )
                        }
                    }
                }
                Status.SUCCESS -> {
                    binding?.progressIndicator?.visibility = View.GONE
                    binding?.relativeSendMoneyButtonWrapper?.apply {
                        context?.let { cont ->
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseBlue
                                )
                            )
                        }
                    }
                    val accountHolderName = binding?.editFullName?.text.toString()
                    val email = if (binding?.editEmail?.text.toString().isNotEmpty())
                        binding?.editEmail?.text.toString() else null
                    val aliPayNumber = binding?.editPaymentTypeNumber?.text.toString()
                    onRecipientCreated?.onRecipientCreated(item!!.id, accountHolderName, email, aliPayNumber)
                    dismiss()
                }
                Status.FAILED -> {
                    isRequest = false
                }
            }
        })

        createNewRecipientViewModel.getDataByEmail().observe(this, Observer { (status, item) ->
            when(status) {
                Status.ON_PROGRESS -> {
                    binding?.relativeSendMoneyButtonWrapper?.apply {
                        context?.let { cont ->
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseGrey61
                                )
                            )
                        }
                    }
                    showLongToast("TransferWise said that cannot create recipient when transfer to CNY, so this is just simulation but send to GBP")
                }
                Status.SUCCESS -> {
                    binding?.progressIndicator?.visibility = View.GONE
                    binding?.relativeSendMoneyButtonWrapper?.apply {
                        context?.let { cont ->
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseBlue
                                )
                            )
                        }
                    }
                    val accountHolderName = binding?.editFullName?.text.toString()
                    val email = if (binding?.editEmail?.text.toString().isNotEmpty())
                        binding?.editEmail?.text.toString() else null
                    val aliPayNumber = binding?.editPaymentTypeNumber?.text.toString()
                    onRecipientCreated?.onRecipientCreated(item!!.id, accountHolderName, email, aliPayNumber)
                    dismiss()
                }
                Status.FAILED -> {
                    isRequest = false
                }
            }
        })
    }

    private fun refreshPage(
        accountHolderName: String,
        currency: String,
        type: String,
        legalType: String? = null,
        cardNumber: String? = null,
        isByEmail: Boolean = false,
        email: String? = null
    ) {
        if (!isRequest) {
            isRequest = true
            binding?.progressIndicator?.visibility = View.VISIBLE
            val itemList = item.fromJsonToList<User>()
            itemList?.get(0)?.id?.let {
                if (!isByEmail) {
                    createNewRecipientViewModel.setData(
                        it,
                        accountHolderName,
                        currency,
                        type,
                        legalType,
                        cardNumber
                    )
                } else {
                    createNewRecipientViewModel.setData(
                        it,
                        accountHolderName,
                        currency,
                        type,
                        legalType,
                        cardNumber,
                        isByEmail,
                        email
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    interface OnRecipientCreated {
        fun onRecipientCreated(
            id: Int,
            accountHolderName: String,
            email: String?,
            aliPayNumber: String
        )
    }

    companion object {
        @JvmStatic
        val TAG: String = SendMoneyToSomeoneElseFragment::class.java.name
        private const val ITEM = "ITEM"

        @JvmStatic
        fun newInstance(item: String) =
            SendMoneyToSomeoneElseFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM, item)
                }
            }
    }
}