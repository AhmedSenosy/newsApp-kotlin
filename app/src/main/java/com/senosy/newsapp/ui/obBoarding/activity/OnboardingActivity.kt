package com.senosy.newsapp.ui.obBoarding.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.senosy.newsapp.R
import com.senosy.newsapp.utils.PREF_FIRST_TIME
import com.senosy.newsapp.utils.PreferenceHelper
import com.senosy.newsapp.utils.PreferenceHelper.set

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        supportActionBar?.hide()
        setupActionBarWithNavController(findNavController(R.id.nav_host_onboarding))
        PreferenceHelper.getPrefs(applicationContext)[PREF_FIRST_TIME] = true
    }
}