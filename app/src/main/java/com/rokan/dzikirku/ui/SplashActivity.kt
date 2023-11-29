package com.rokan.dzikirku.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.rokan.dzikirku.MainActivity
import com.rokan.dzikirku.R

class SplashActivity : AppCompatActivity() {
    val SPLASH_TIME_OUT:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val color = ContextCompat.getColor(this, R.color.bgm)
        progressBar.indeterminateTintList = ColorStateList.valueOf(color)

        Handler().postDelayed({
            startActivity(Intent(this , MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}