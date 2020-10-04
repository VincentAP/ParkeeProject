package com.parkee.login.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.parkee.assets.extensions.animateClicked
import com.parkee.assets.extensions.fromJsonToList
import com.parkee.assets.foundations.BaseFullScreenDialogFragment
import com.parkee.assets.model.User
import com.parkee.homepage.view.activity.HomepageActivity
import com.parkee.login.databinding.VerificationFragmentBinding

class VerificationFragment: BaseFullScreenDialogFragment() {

    private var binding: VerificationFragmentBinding? = null
    private val item by extraNotNull(ITEM, "{}")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VerificationFragmentBinding.inflate(inflater, container, false)

        binding?.apply {
            val itemList = item.fromJsonToList<User>()
            itemList?.get(0)?.details?.phoneNumber?.let {
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
                binding?.apply {
                    context?.let { cont ->
                        relativeDoneButtonWrapper.setBackgroundColor(
                            ContextCompat.getColor(
                                cont,
                                com.parkee.assets.R.color.transferWiseGrey61
                            )
                        )
                    }
                    progressIndicator.visibility = View.VISIBLE
                }
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
                Intent(context, HomepageActivity::class.java).also {
                    it.putExtra("ITEM", item)
                    startActivity(it)
                }
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
        private const val ITEM = "ITEM"

        @JvmStatic
        fun newInstance(item: String?) =
            VerificationFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM, item)
                }
            }
    }
}