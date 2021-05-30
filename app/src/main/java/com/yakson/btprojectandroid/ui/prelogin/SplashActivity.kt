package com.yakson.btprojectandroid.ui.prelogin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.yakson.btprojectandroid.R

class SplashActivity : AppCompatActivity() {

    private var TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_OUT)
    }
}