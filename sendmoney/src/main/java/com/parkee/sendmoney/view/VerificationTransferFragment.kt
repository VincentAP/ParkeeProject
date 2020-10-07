package com.parkee.sendmoney.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.parkee.assets.extensions.toJson
import com.parkee.assets.foundations.BaseFullScreenDialogFragment
import com.parkee.assets.repo.Status
import com.parkee.sendmoney.R
import com.parkee.sendmoney.databinding.VerificationTransferFragmentBinding
import com.parkee.sendmoney.viewmodel.VerificationTransferViewModelImpl

class VerificationTransferFragment: BaseFullScreenDialogFragment() {

    private var binding: VerificationTransferFragmentBinding? = null
    private lateinit var verificationTransferViewModel: VerificationTransferViewModelImpl
    private var isPasswordShowed = false
    private var passwordHideBG: Drawable? = null
    private var passwordShowedBG: Drawable? = null
    private val profileId by extraNotNull(PROFILE_ID, 0)
    private val transferId by extraNotNull(TRANSFER_ID, 0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VerificationTransferFragmentBinding.inflate(inflater, container, false)

        context?.let {
            passwordHideBG = ContextCompat.getDrawable(it, R.drawable.ic_hide)
            passwordShowedBG = ContextCompat.getDrawable(it, R.drawable.ic_show)
        }

        binding?.imageShowHidePassword?.setOnClickListener {
            if (!isPasswordShowed) {
                binding?.imageShowHidePassword?.background = passwordHideBG
                binding?.editPassword?.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                isPasswordShowed = true
            } else {
                binding?.imageShowHidePassword?.background = passwordShowedBG
                binding?.editPassword?.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                isPasswordShowed = false
            }
        }

        binding?.buttonDone?.setOnClickListener {
            binding?.progressIndicator?.visibility = View.VISIBLE
            if (binding?.editPassword?.text?.toString() == CREDENTIALS_PASSWORD) {
                verificationTransferViewModel.createFundTransfer(profileId, transferId)
            } else {
                binding?.progressIndicator?.visibility = View.GONE
            }
        }

        binding?.layoutLoginToolbar?.imageNavigation?.setOnClickListener {
            dismiss()
        }

        setupVerificationTransferViewModel()
        return binding?.root
    }

    private fun setupVerificationTransferViewModel() {
        verificationTransferViewModel = VerificationTransferViewModelImpl()
        verificationTransferViewModel.getFundTransferResult().observe(this, Observer { (status, item) ->
            when(status) {
                Status.ON_PROGRESS -> {
                    binding?.apply {
                        context?.let { cont ->
                            relativeDoneButtonWrapper.setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseGrey61
                                )
                            )
                        }
                    }
                }
                Status.SUCCESS -> {
                    showLongToast("Congratz, your transfer success!!")
                    binding?.apply {
                        context?.let { cont ->
                            relativeDoneButtonWrapper.setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseBlue
                                )
                            )
                        }
                        progressIndicator.visibility = View.GONE
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        dismiss()
                    }, 1500)
                }
                Status.FAILED -> showShortToast("Failed")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = VerificationTransferFragment::class.java.name
        private const val CREDENTIALS_PASSWORD = "vincent123"
        private const val PROFILE_ID = "PROFILE_ID"
        private const val TRANSFER_ID = "TRANSFER_ID"

        @JvmStatic
        fun newInstance(
            profileId: Int,
            transferId: Int
        ) = VerificationTransferFragment().apply {
            arguments = Bundle().apply {
                putInt(TRANSFER_ID, transferId)
                putInt(PROFILE_ID, profileId)
            }
        }
    }
}