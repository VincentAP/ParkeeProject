package com.parkee.parkeeproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.parkee.login.view.LoginFragment
import com.parkee.parkeeproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            LoginFragment.newInstance()
                .show(supportFragmentManager, LoginFragment.TAG)
        }
    }
}