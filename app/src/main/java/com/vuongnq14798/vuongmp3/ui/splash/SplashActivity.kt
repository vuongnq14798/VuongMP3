package com.vuongnq14798.vuongmp3.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vuongnq14798.vuongmp3.MainActivity

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(MainActivity.getIntent(this))
        finish()
    }
}
