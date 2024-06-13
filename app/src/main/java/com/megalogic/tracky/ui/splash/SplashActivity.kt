package com.megalogic.tracky.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.megalogic.tracky.R
import com.megalogic.tracky.data.PreferenceManager
import com.megalogic.tracky.ui.login.LoginActivity
import com.megalogic.tracky.ui.admin.AdminMainActivity
import com.megalogic.tracky.ui.pic.PICMainActivity
import com.megalogic.tracky.ui.user.UserMainActivity
import com.megalogic.tracky.utils.JWTDecoder

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
                try {
                    val decodedToken = JWTDecoder.decoded(token)
                    val role = decodedToken["role"] as String
                    Log.d("SplashActivity", "User role: $role")
                    navigateToMain(role)
                } catch (e: Exception) {
                    Log.e("SplashActivity", "Error decoding token: ${e.message}")
                    navigateToLogin()
                }
            } else {
                navigateToLogin()
            }
        }, splashTimeOut)
    }

    private fun navigateToMain(role: String) {
        val intent = when (role) {
            "admin" -> Intent(this, AdminMainActivity::class.java)
            "pic" -> Intent(this, PICMainActivity::class.java)
            "user" -> Intent(this, UserMainActivity::class.java)
            else -> {
                Log.w("SplashActivity", "Unknown role: $role, navigating to Login")
                Intent(this, LoginActivity::class.java)
            }
        }
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}