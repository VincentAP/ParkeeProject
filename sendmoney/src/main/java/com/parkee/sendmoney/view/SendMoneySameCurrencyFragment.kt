package com.parkee.sendmoney.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.parkee.assets.extensions.fromJsonToList
import com.parkee.assets.extensions.toCurrencyFormat
import com.parkee.assets.foundations.BaseFragment
import com.parkee.assets.model.User
import com.parkee.assets.repo.Status
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.SendMoneySameCurrencyFragmentBinding
import com.parkee.sendmoney.viewmodel.SendMoneyViewModelImpl

class SendMoneySameCurrencyFragment: BaseFragment() {

    private var binding: SendMoneySameCurrencyFragmentBinding? = null
    private lateinit var sendMoneyViewModel: SendMoneyViewModelImpl
    private val item1 by extraNotNull(ITEM, "{}")
    private var currentTargetAmount = 1000.toDouble()
    private var feeItem: String? = null
    private var currencyItem: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SendMoneySameCurrencyFragmentBinding.inflate(inflater, container, false)

        binding?.layoutInsertTargetItem?.editTargetValue?.apply {
            setText(currentTargetAmount.toCurrencyFormat())
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        s?.toString()?.let {
                            val re = Regex("[^A-Za-z0-9 ]")
                            val currency = re.replace(it, "")
                            if (currency.isNotEmpty() && currency.toDouble() != currentTargetAmount) {
                                currentTargetAmount = currency.toDouble()
                                refreshPage(currentTargetAmount)
                            } else if (currency.isEmpty()) {
                                currentTargetAmount = 0.0
                                refreshPage(currentTargetAmount)
                            }
                            val pos = binding?.layoutInsertTargetItem?.editTargetValue?.text?.length
                            if (pos != null) {
                                binding?.layoutInsertTargetItem?.editTargetValue?.setSelection(pos)
                            }
                        }
                    }, 500)
                }

                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {}

                override fun onTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {}
            })
        }

        binding?.layoutInsertTargetItem?.linearDropDownWrapper?.setOnClickListener {
            navigate(binding?.layoutInsertTargetItem?.textTargetCurrency?.text?.toString())
        }

        binding?.layoutExpanding?.linearTransferTypeDropDownWrapper?.setOnClickListener {
            activity?.supportFragmentManager?.let {
                TransferTypeFragment.newInstance(
                    feeItem,
                    currencyItem
                )
                    .setOnTransferTypeClicked(object : TransferTypeFragment.OnTransferTypeClicked {
                        override fun onTransferTypeClicked(type: String) {
                            binding?.layoutExpanding?.textTargetTransferTypeCurrency?.text = type
                        }
                    })
                    .show(it, TransferTypeFragment.TAG)
            }
        }

        setupSendMoneySameCurrencyViewModel()
        return binding?.root
    }

    private fun navigate(item: String?) {
        activity?.supportFragmentManager?.let {
            AvailableCurrencyFragment.newInstance(item)
                .show(it, AvailableCurrencyFragment.TAG)
        }
    }

    private fun setupSendMoneySameCurrencyViewModel() {
        sendMoneyViewModel = SendMoneyViewModelImpl()
        sendMoneyViewModel.getQuote().observe(this, Observer { (status, item) ->
            when(status) {
                Status.ON_PROGRESS -> {
                    binding?.coordinatorProgressBar?.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding?.apply {
                        coordinatorProgressBar.visibility = View.GONE
                        scrollWrapper.visibility = View.VISIBLE
                        feeItem = item?.fee
                        currencyItem = item?.sourceCurrency

                        layoutInsertTargetItem.apply {
                            textTargetCurrency.text = item?.targetCurrency
                        }
                        layoutExpanding.apply {
                            textFeeValue.text = item?.fee
                            textTargetTransferTypeCurrency.text = item?.type
                            textInitial.text = setSenderInitial()
                            textFinalValue.text = item?.sourceAmount
                        }
                        textArrivedTime.text = item?.deliveryEstimate
                    }
                }
                Status.FAILED -> showShortToast("Please insert right amount")
            }
        })

        sendMoneyViewModel.getAccountBalance().observe(this, Observer {
            val amount = "${it.amount.toCurrencyFormat()} GBP"
            binding?.layoutInsertTargetItem?.textCurrentBalance?.text =
                HtmlCompat.fromHtml(
                    getString(R.string.current_balance, amount),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
        })
    }

    private fun setSenderInitial(): String {
        var initial = ""
        val itemList = item1.fromJsonToList<User>()
        itemList?.get(0)?.details?.let {
            initial = "${it.firstName?.get(0)}${it.lastName?.get(0)}"
        }
        return initial
    }

    private fun refreshPage(targetAmount: Double) {
        val itemList = item1.fromJsonToList<User>()
        itemList?.get(0)?.id?.let {
            sendMoneyViewModel.setQuote(
                it,
                targetAmount,
                true
            )
            sendMoneyViewModel.setAccountBalance(it)
        }
    }

    override fun onStart() {
        super.onStart()
        refreshPage(currentTargetAmount)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = SendMoneySameCurrencyFragment::class.java.name
        private const val ITEM = "ITEM"

        @JvmStatic
        fun newInstance(item: String?) =
            SendMoneySameCurrencyFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM, item)
                }
            }
    }
}