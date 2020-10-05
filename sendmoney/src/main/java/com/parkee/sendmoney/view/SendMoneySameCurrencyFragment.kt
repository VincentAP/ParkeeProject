package com.parkee.sendmoney.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseFragment
import com.parkee.sendmoney.databinding.SendMoneySameCurrencyFragmentBinding

class SendMoneySameCurrencyFragment: BaseFragment() {

    private var binding: SendMoneySameCurrencyFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SendMoneySameCurrencyFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = SendMoneySameCurrencyFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            SendMoneySameCurrencyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}