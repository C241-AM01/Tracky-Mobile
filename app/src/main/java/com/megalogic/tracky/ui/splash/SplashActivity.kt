package com.megalogic.tracky.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.megalogic.tracky.R
import com.megalogic.tracky.data.PreferenceManager
import com.megalogic.tracky.ui.login.LoginActivity
import com.megalogic.tracky.ui.admin.AdminMainActivity

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 3000
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        preferenceManager = PreferenceManager(this)

        Handler().postDelayed({
            val token = preferenceManager.getToken()
            if (token != null) {
                navigateToMain()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, splashTimeOut)
    }

    private fun navigateToMain() {
        val intent = Intent(this, AdminMainActivity::class.java) // Ubah sesuai dengan peran pengguna
        startActivity(intent)
        finish()
    }
}
