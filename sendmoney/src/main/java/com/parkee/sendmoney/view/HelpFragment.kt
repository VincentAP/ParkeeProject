package com.parkee.sendmoney.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkee.assets.foundations.BaseDialogFragment
import com.parkee.sendmoney.databinding.HelpDialogFragmentBinding

class HelpFragment: BaseDialogFragment() {

    private var binding: HelpDialogFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HelpDialogFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = HelpFragment::class.java.name

        @JvmStatic
        fun newInstance() = HelpFragment()
    }
}