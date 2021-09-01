package com.senosy.newsapp.ui.obBoarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.senosy.newsapp.R

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        supportActionBar?.hide()
    }
}