package com.megalogic.tracky.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.megalogic.tracky.R
import com.megalogic.tracky.data.PreferenceManager
import com.megalogic.tracky.ui.login.LoginActivity
import com.megalogic.tracky.utils.JWTDecoder

class ProfileActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        preferenceManager = PreferenceManager(this)
        loadProfileImage()
        displayRoleName()
        setupLogoutButton()
    }

    private fun loadProfileImage() {
        val token = preferenceManager.getToken()
        if (token != null) {
            val decodedToken = JWTDecoder.decoded(token)
            val role = decodedToken["role"] as String
            val imageView: ImageView = findViewById(R.id.iv_avatar)
            val imageResource = when (role) {
                "admin" -> R.drawable.admin_photo
                "pic" -> R.drawable.pic_photo
                "user" -> R.drawable.user_photo
                else -> R.drawable.default_photo
            }
            imageView.setImageResource(imageResource)
        }
    }

    private fun displayRoleName() {
        val token = preferenceManager.getToken()
        if (token != null) {
            val decodedToken = JWTDecoder.decoded(token)
            val role = decodedToken["role"] as String
            val textView: TextView = findViewById(R.id.tv_role)
            textView.text = when (role) {
                "admin" -> "Admin"
                "pic" -> "PIC"
                "user" -> "User"
                else -> "Unknown"
            }
        }
    }

    private fun setupLogoutButton() {
        findViewById<MaterialButton>(R.id.btn_logout).setOnClickListener {
            preferenceManager.clearToken()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}