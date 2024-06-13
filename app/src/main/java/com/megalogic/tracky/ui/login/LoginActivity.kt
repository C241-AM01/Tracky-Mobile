package com.megalogic.tracky.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.megalogic.tracky.data.PreferenceManager
import com.megalogic.tracky.data.api.ApiConfig
import com.megalogic.tracky.data.model.LoginRequest
import com.megalogic.tracky.databinding.ActivityLoginBinding
import com.megalogic.tracky.ui.admin.AdminMainActivity
import com.megalogic.tracky.ui.pic.PICMainActivity
import com.megalogic.tracky.ui.role.RoleActivity
import com.megalogic.tracky.ui.user.UserMainActivity
import com.megalogic.tracky.utils.JWTDecoder
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
                val intent = Intent(this@LoginActivity, RoleActivity::class.java)
                startActivity(intent)
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
                val loginRequest = LoginRequest(email, password)
                Log.d("LoginRequest", "Login attempt with email: $email & password: $password")

                val response = ApiConfig.getApiService().userLogin(loginRequest)
                withContext(Dispatchers.Main) {
                    Log.d("LoginResponse", "Token: ${response.token}")
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                }

                val decodedToken = JWTDecoder.decoded(response.token)
                val role = decodedToken["role"] as String

                preferenceManager.saveToken(response.token)

                navigateBasedOnRole(role)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("LoginError", "Login failed: ${e.message}")
                    Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateBasedOnRole(role: String) {
        val intent = when (role) {
            "admin" -> Intent(this, AdminMainActivity::class.java)
            "pic" -> Intent(this, PICMainActivity::class.java)
            "user" -> Intent(this, UserMainActivity::class.java)
            else -> {
                Toast.makeText(this, "Unknown role: $role", Toast.LENGTH_SHORT).show()
                return
            }
        }
        startActivity(intent)
        finish()
    }
}