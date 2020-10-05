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
import com.parkee.assets.model.Quote
import com.parkee.assets.model.User
import com.parkee.assets.repo.Status
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.SendMoneyInternationalFragmentBinding
import com.parkee.sendmoney.viewmodel.SendMoneyInternationalViewModelImpl

class SendMoneyInternationalFragment: BaseFragment() {

    private var binding: SendMoneyInternationalFragmentBinding? = null
    private lateinit var sendMoneyInternationalViewModel: SendMoneyInternationalViewModelImpl
    private val item by extraNotNull(ITEM, "{}")
    private var currentSourceAmount = 1000.toDouble()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SendMoneyInternationalFragmentBinding.inflate(inflater, container, false)
        binding?.layoutInsertSourceItem?.editSourceValue?.apply {
            setText(currentSourceAmount.toCurrencyFormat())
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        s?.toString()?.let {
                            val re = Regex("[^A-Za-z0-9 ]")
                            val currency = re.replace(it, "")
                            if (currency.isNotEmpty() && currency.toDouble() != currentSourceAmount) {
                                currentSourceAmount = currency.toDouble()
                                refreshPage(currentSourceAmount)
                            } else if (currency.isEmpty()) {
                                currentSourceAmount = 0.0
                                refreshPage(currentSourceAmount)
                            }
                            val pos = binding?.layoutInsertSourceItem?.editSourceValue?.text?.length
                            if (pos != null) {
                                binding?.layoutInsertSourceItem?.editSourceValue?.setSelection(pos)
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
        setupSendMoneyInternationalViewModel()
        return binding?.root
    }

    private fun setupSendMoneyInternationalViewModel() {
        sendMoneyInternationalViewModel = SendMoneyInternationalViewModelImpl()
        sendMoneyInternationalViewModel.getQuote().observe(this, Observer { (status, item) ->
            when(status) {
                Status.ON_PROGRESS -> {
                    binding?.coordinatorProgressBar?.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding?.apply {
                        coordinatorProgressBar.visibility = View.GONE
                        scrollWrapper.visibility = View.VISIBLE

                        layoutInsertSourceItem.apply {
                            textSourceCurrency.text = item?.sourceCurrency
                        }
                        layoutExpanding.apply {
                            textFeeValue.text = item?.fee
                            textSourceTransferTypeCurrency.text = item?.type
                            textConvertValue.text = item?.subtractResult
                            textExchangeRate.text = item?.exchangeRate
                        }
                        layoutInsertRecipientItem.apply {
                            editTargetValue.setText(item?.targetAmount)
                            textTargetCurrency.text = item?.targetCurrency
                        }
                        textArrivedTime.text = item?.deliveryEstimate
                    }
                }
                Status.FAILED -> showShortToast("Please insert right amount")
            }
        })

        sendMoneyInternationalViewModel.getAccountBalance().observe(this, Observer {
            val amount = "${(it.amount - currentSourceAmount).toCurrencyFormat()} GBP"
            binding?.layoutInsertSourceItem?.textCurrentBalance?.text =
                HtmlCompat.fromHtml(
                    getString(R.string.current_balance, amount),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
        })
    }

    private fun refreshPage(sourceAmount: Double) {
        val itemList = item.fromJsonToList<User>()
        itemList?.get(0)?.id?.let {
            sendMoneyInternationalViewModel.setQuote(it, sourceAmount)
            sendMoneyInternationalViewModel.setAccountBalance(it)
        }
    }

    override fun onStart() {
        super.onStart()
        refreshPage(currentSourceAmount)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = SendMoneyInternationalFragment::class.java.name
        private const val ITEM = "ITEM"

        @JvmStatic
        fun newInstance(item: String?) =
            SendMoneyInternationalFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM, item)
                }
            }
    }
}