package com.parkee.sendmoney.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseFragment
import com.parkee.sendmoney.databinding.SendMoneyInternationalFragmentBinding

class SendMoneyInternationalFragment: BaseFragment() {

    private var binding: SendMoneyInternationalFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SendMoneyInternationalFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = SendMoneyInternationalFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            SendMoneyInternationalFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}