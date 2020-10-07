package com.parkee.sendmoney.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.parkee.assets.foundations.BaseItem
import com.parkee.assets.foundations.BaseRecyclerAdapter
import com.parkee.assets.viewholder.HeaderSectionViewHolderFactory
import com.parkee.sendmoney.databinding.AvailableCurrencyFragmentBinding
import com.parkee.sendmoney.viewholder.AvailableCurrencyItem
import com.parkee.sendmoney.viewholder.AvailableCurrencyViewHolderFactory
import com.parkee.sendmoney.viewmodel.AvailableCurrencyViewModelImpl

class AvailableCurrencyFragment: BottomSheetDialogFragment() {

    private var binding: AvailableCurrencyFragmentBinding? = null
    private lateinit var availableCurrencyViewModel: AvailableCurrencyViewModelImpl
    private var listenerUSD: Listener? = null

    private val listener: (BaseItem, View) -> Unit = { item,_->
        setOnClick(item)
    }

    private val recyclerAdapter = BaseRecyclerAdapter(
        listener, listOf(
            HeaderSectionViewHolderFactory(),
            AvailableCurrencyViewHolderFactory()
        )
    )

    fun setOnListenerUSD(listenerUSD: Listener) = apply {
        this.listenerUSD = listenerUSD
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AvailableCurrencyFragmentBinding.inflate(inflater, container, false)
        binding?.recyclerAvailableCurrency?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerAdapter
        }

        binding?.editSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        setupAvailableCurrencyViewModel()
        return binding?.root
    }

    private fun setOnClick(item: BaseItem) {
        when(item) {
            is AvailableCurrencyItem -> {
                if (item.currency != "CNY" && item.currency != "GBP" && item.currency != "USD") {
                    Toast.makeText(
                        context,
                        "Currently we only can provide transfer from GBP to CNY",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (item.currency == "USD") listenerUSD?.listener()
                    dismiss()
                }
            }
        }
    }

    private fun filter(text: String) {
        availableCurrencyViewModel.searchItem(text)
    }

    private fun setupAvailableCurrencyViewModel() {
        availableCurrencyViewModel = AvailableCurrencyViewModelImpl()
        availableCurrencyViewModel.getData().observe(this, Observer {
            recyclerAdapter.submitList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        arguments?.getString(ITEM)?.let {
            availableCurrencyViewModel.setData(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    interface Listener {
        fun listener()
    }

    companion object {
        @JvmStatic
        val TAG: String = AvailableCurrencyFragment::class.java.name
        private const val ITEM = "ITEM"

        @JvmStatic
        fun newInstance(item: String?) =
            AvailableCurrencyFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM, item)
                }
            }
    }
}