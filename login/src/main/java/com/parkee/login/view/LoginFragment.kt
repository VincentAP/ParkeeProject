package com.parkee.login.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.parkee.assets.extensions.animateClicked
import com.parkee.assets.extensions.toJson
import com.parkee.assets.foundations.BaseFullScreenDialogFragment
import com.parkee.assets.model.User
import com.parkee.assets.repo.*
import com.parkee.login.R
import com.parkee.login.databinding.LoginFragmentBinding
import com.parkee.login.viewmodel.LoginViewModelImpl
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment: BaseFullScreenDialogFragment() {

    private var binding: LoginFragmentBinding? = null
    private lateinit var loginViewModel: LoginViewModelImpl
    private var isPasswordShowed = false
    private var passwordHideBG: Drawable? = null
    private var passwordShowedBG: Drawable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)

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

        binding?.apply {
            textTermsPolicy.text =
                HtmlCompat.fromHtml(
                    getString(R.string.terms_policy),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
        }

        binding?.buttonLogin?.setOnClickListener {
            binding?.buttonLogin?.animateClicked()
            binding?.progressIndicator?.visibility = View.VISIBLE
            if (binding?.editEmail?.text?.toString() == CREDENTIALS_EMAIL &&
                binding?.editPassword?.text?.toString() == CREDENTIALS_PASSWORD) {
                binding?.layoutCredentialsError?.root?.visibility = View.GONE
                loginViewModel.requestLogin()
            } else {
                binding?.layoutCredentialsError?.root?.visibility = View.VISIBLE
                binding?.progressIndicator?.visibility = View.GONE
            }
        }

        binding?.layoutCredentialsError?.imageClose?.setOnClickListener {
            binding?.layoutCredentialsError?.root?.visibility = View.GONE
        }

        binding?.layoutLoginToolbar?.imageNavigation?.setOnClickListener {
            dismiss()
        }

        setupLoginViewModel()
        return binding?.root
    }

    private fun setupLoginViewModel() {
        loginViewModel = LoginViewModelImpl()
        loginViewModel.getLoginResponse().observe(this, Observer { (status, response) ->
            when (status) {
                Status.ON_PROGRESS -> {
                    binding?.apply {
                        context?.let { cont ->
                            relativeLoginButtonWrapper.setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseGrey61
                                )
                            )
                        }
                    }
                }
                Status.SUCCESS -> {
                    activity?.supportFragmentManager?.let {
                        VerificationFragment.newInstance(
                            response?.toJson()
                        )
                            .show(it, VerificationFragment.TAG)
                    }
                    binding?.apply {
                        context?.let { cont ->
                            relativeLoginButtonWrapper.setBackgroundColor(
                                ContextCompat.getColor(
                                    cont,
                                    com.parkee.assets.R.color.transferWiseBlue
                                )
                            )
                        }
                        progressIndicator.visibility = View.GONE
                    }
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
        val TAG: String = LoginFragment::class.java.name
        private const val CREDENTIALS_EMAIL = "ardyanputra.vincent@yahoo.co.id"
        private const val CREDENTIALS_PASSWORD = "vincent123"

        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}