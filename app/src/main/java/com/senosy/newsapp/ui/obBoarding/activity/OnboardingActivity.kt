package com.senosy.newsapp.ui.obBoarding.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.senosy.newsapp.R
import com.senosy.newsapp.ui.MainActivity
import com.senosy.newsapp.utils.PREF_FIRST_TIME
import com.senosy.newsapp.utils.PreferenceHelper
import com.senosy.newsapp.utils.PreferenceHelper.set

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        supportActionBar?.hide()
        checkFirstTime()
    }

    private fun checkFirstTime(){
        val isFirstTime = PreferenceHelper.getPrefs(applicationContext).getBoolean(PREF_FIRST_TIME,true)
        if(!isFirstTime)
        {
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }
        else
        {
            setupActionBarWithNavController(findNavController(R.id.nav_host_onboarding))
        }
    }
}