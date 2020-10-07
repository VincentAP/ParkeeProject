package com.parkee.homepage.view.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.parkee.assets.foundations.BaseActivity
import com.parkee.homepage.R
import com.parkee.homepage.databinding.HomepageActivityBinding
import com.parkee.homepage.navigator.HomepageNavigator
import com.parkee.sendmoney.view.SendMoneyLandingActivity
import kotlinx.android.synthetic.main.homepage_activity.*

class HomepageActivity: BaseActivity() {

    private lateinit var binding: HomepageActivityBinding

    private var homeNavSelectedBG: Drawable? = null
    private var cardNavSelectedBG: Drawable? = null
    private var recipientNavSelectedBG: Drawable? = null
    private var homeNavUnselectedBG: Drawable? = null
    private var cardNavUnselectedBG: Drawable? = null
    private var recipientNavUnselectedBG: Drawable? = null
    private var selectedTextColor: Int? = null
    private var unselectedTextColor: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigate()
        initBottomNavView()

        with(binding.layoutBottomNavigation) {
            setSupportActionBar(toolbarHomepage)
            linearHomeNavWrapper.setOnClickListener {
                updateBottomNavView(HOME_FRAGMENT)
                navigate()
            }
            linearCardNavWrapper.setOnClickListener {
                updateBottomNavView(CARD_FRAGMENT)
            }
            linearRecipientsNavWrapper.setOnClickListener {
                updateBottomNavView(RECIPIENTS_FRAGMENT)
                navigate(RECIPIENTS_FRAGMENT)
            }
            linearSendNavWrapper.setOnClickListener {
                updateBottomNavView(SEND_FRAGMENT)
                navigate(SEND_FRAGMENT)
            }
        }
    }

    private fun initBottomNavView() {
        homeNavSelectedBG = ContextCompat.getDrawable(this, R.drawable.ic_selected_home)
        cardNavSelectedBG = ContextCompat.getDrawable(this, R.drawable.ic_selected_card)
        recipientNavSelectedBG = ContextCompat.getDrawable(this, R.drawable.ic_selected_recipients)
        homeNavUnselectedBG = ContextCompat.getDrawable(this, R.drawable.ic_unselected_home)
        cardNavUnselectedBG = ContextCompat.getDrawable(this, R.drawable.ic_unselected_card)
        recipientNavUnselectedBG = ContextCompat.getDrawable(this, R.drawable.ic_unselected_recipients)
        selectedTextColor = ContextCompat.getColor(this, R.color.transferWiseBlue)
        unselectedTextColor = ContextCompat.getColor(this, R.color.transferWiseBlackBlue)
    }

    private fun updateBottomNavView(
        type: String
    ) {
        with(binding.layoutBottomNavigation) {
            when (type) {
                HOME_FRAGMENT -> {
                    imageHome.setImageDrawable(homeNavSelectedBG)
                    selectedTextColor?.let {
                        textHome.setTextColor(it)
                    }
                    imageCard.setImageDrawable(cardNavUnselectedBG)
                    imageRecipients.setImageDrawable(recipientNavUnselectedBG)
                    unselectedTextColor?.let {
                        textCard.setTextColor(it)
                        textRecipients.setTextColor(it)
                    }
                }
                CARD_FRAGMENT -> {
                    imageCard.setImageDrawable(cardNavSelectedBG)
                    selectedTextColor?.let {
                        textCard.setTextColor(it)
                    }
                    imageHome.setImageDrawable(homeNavUnselectedBG)
                    imageRecipients.setImageDrawable(recipientNavUnselectedBG)
                    unselectedTextColor?.let {
                        textHome.setTextColor(it)
                        textRecipients.setTextColor(it)
                        textSend.setTextColor(it)
                    }
                }
                RECIPIENTS_FRAGMENT -> {
                    imageRecipients.setImageDrawable(recipientNavSelectedBG)
                    selectedTextColor?.let {
                        textRecipients.setTextColor(it)
                    }
                    imageHome.setImageDrawable(homeNavUnselectedBG)
                    imageCard.setImageDrawable(cardNavUnselectedBG)
                    unselectedTextColor?.let {
                        textHome.setTextColor(it)
                        textCard.setTextColor(it)
                        textSend.setTextColor(it)
                    }
                }
                else -> {
                    selectedTextColor?.let {
                        textSend.setTextColor(it)
                    }
                    imageRecipients.setImageDrawable(recipientNavUnselectedBG)
                    imageHome.setImageDrawable(homeNavUnselectedBG)
                    imageCard.setImageDrawable(cardNavUnselectedBG)
                    unselectedTextColor?.let {
                        textHome.setTextColor(it)
                        textCard.setTextColor(it)
                        textRecipients.setTextColor(it)
                    }
                }
            }
        }
    }

    private fun navigate(type: String = HOME_FRAGMENT) {
        when(type) {
            HOME_FRAGMENT -> {
                val intent = intent.getStringExtra("ITEM")
                HomepageNavigator.gotoHomeFragment(
                    supportFragmentManager,
                    R.id.frameFragmentContainer,
                    intent
                )
            }
            RECIPIENTS_FRAGMENT -> {

            }
            SEND_FRAGMENT -> {
                val item = intent.getStringExtra("ITEM")
                Intent(this, SendMoneyLandingActivity::class.java).also {
                    it.putExtra("ITEM", item)
                    startActivity(it)
                }
            }
        }
    }

    companion object {
        private const val HOME_FRAGMENT = "HOME_FRAGMENT"
        private const val CARD_FRAGMENT = "CARD_FRAGMENT"
        private const val RECIPIENTS_FRAGMENT = "RECIPIENTS_FRAGMENT"
        private const val SEND_FRAGMENT = "SEND_FRAGMENT"
    }
}