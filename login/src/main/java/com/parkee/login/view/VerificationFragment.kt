package com.parkee.login.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import com.parkee.assets.extensions.animateClicked
import com.parkee.assets.foundations.BaseFullScreenDialogFragment
import com.parkee.login.databinding.VerificationFragmentBinding

class VerificationFragment: BaseFullScreenDialogFragment() {

    private var binding: VerificationFragmentBinding? = null
    private val phoneNumber by extra<String>(PHONE_NUMBER)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VerificationFragmentBinding.inflate(inflater, container, false)

        binding?.apply {
            phoneNumber?.let {
                val phoneNumberLength = it.length - 4
                var textStar = ""
                for (i in 0 until phoneNumberLength) textStar += "*"
                val text = "${it.replaceRange(0, phoneNumberLength, textStar)}."
                textPhoneNumber.text = text
            }
            edit1.requestFocus()
            edit1.requestEditTextFocus(edit2)
            edit2.requestEditTextFocus(edit3)
            edit3.requestEditTextFocus(edit4)
            edit4.requestEditTextFocus(edit5)
            edit5.requestEditTextFocus(edit6)
            edit6.requestEditTextFocus()

            buttonDone.setOnClickListener {
                it.animateClicked()
                checkIntegrity()
            }

            layoutLoginToolbar.imageNavigation.setOnClickListener {
                dismiss()
            }
        }

        return binding?.root
    }

    private fun AppCompatEditText.requestEditTextFocus(
        nextEditId: AppCompatEditText? = null
    ) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    if (nextEditId != null) nextEditId.requestFocus()
                    else checkIntegrity()
                }
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

    private fun checkIntegrity() {
        binding?.apply {
            if (edit1.text?.toString() == OTP &&
                edit2.text?.toString() == OTP  &&
                edit3.text?.toString() == OTP  &&
                edit4.text?.toString() == OTP  &&
                edit5.text?.toString() == OTP  &&
                edit6.text?.toString() == OTP ) {
                showShortToast("Good")
            } else showShortToast("Please enter the right OTP code")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        @JvmStatic
        val TAG: String = VerificationFragment::class.java.name
        private const val OTP = "1"
        private const val PHONE_NUMBER = "PHONE_NUMBER"

        @JvmStatic
        fun newInstance(phoneNumber: String?) =
            VerificationFragment().apply {
                arguments = Bundle().apply {
                    putString(PHONE_NUMBER, phoneNumber)
                }
            }
    }
}