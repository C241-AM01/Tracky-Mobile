package com.megalogic.tracky.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.megalogic.tracky.data.PreferenceManager
import com.megalogic.tracky.data.api.ApiConfig
import com.megalogic.tracky.databinding.ActivityLoginBinding
import com.megalogic.tracky.ui.admin.AdminMainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)
        setActions()
    }

    private fun setActions() {
        binding.apply {
            btnRegister.setOnClickListener {
                // Handle register click
            }
            btnLogin.setOnClickListener {
                val email = inputEmail.editText?.text.toString()
                val password = inputPassword.editText?.text.toString()
                handleLogin(email, password)
            }
        }
    }

    private fun handleLogin(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiConfig.getApiService().userLogin(email, password)
                withContext(Dispatchers.Main) {
                    Log.d("LoginActivity", "Token: ${response.token}")
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                }
                preferenceManager.saveToken(response.token)
                navigateToMain()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("LoginActivity", "Login failed: ${e.message}")
                    Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToMain() {
        // Navigasi ke halaman utama berdasarkan peran pengguna
        val intent = Intent(this, AdminMainActivity::class.java) // Ubah sesuai dengan peran pengguna
        startActivity(intent)
        finish()
    }
}
