package com.uchihan.smartb_card.presentation.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.uchihan.smartb_card.MainActivity
import com.uchihan.smartb_card.R
import com.uchihan.smartb_card.common.Constants
import com.uchihan.smartb_card.presentation.ui.registration.RegistrationActivity
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        launchScreen()
    }

    private fun launchScreen() {
        Handler().postDelayed({
            if (sharedPreferences.getString(Constants.REGISTERED_USER, null) != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, RegistrationActivity::class.java))
                finish()
            }
        },3000)

    }
}