package com.megalogic.tracky.ui.splash

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.megalogic.tracky.R
import android.content.Intent
import android.os.Handler
import com.megalogic.tracky.MainActivity
import com.megalogic.tracky.databinding.ActivitySplashBinding
import com.megalogic.tracky.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 3000
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, splashTimeOut)
    }
}