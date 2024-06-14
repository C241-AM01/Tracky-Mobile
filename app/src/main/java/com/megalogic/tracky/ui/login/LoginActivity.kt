package com.megalogic.tracky.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.megalogic.tracky.data.PreferenceManager
import com.megalogic.tracky.data.ResultState
import com.megalogic.tracky.data.ViewModelFactory
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

    private lateinit var factory: ViewModelFactory
    private val viewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)


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
                newLogin(email, password)
            }
        }
    }

    private fun newLogin(email: String, password: String) {
        viewModel.login(email, password)
        viewModel.responseLogin.observe(this) { response ->
            when (response) {
                is ResultState.Loading -> {
                    Log.d("Login", "Loading...")
                    binding.progressBar.visibility = android.view.View.VISIBLE
                }

                is ResultState.Success -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                    val token = response.data.token
                    val decoded = token?.let { JWTDecoder.decoded(it) }
                    val role = decoded?.get("role") as String
//                    Toast.makeText(this, "Role: $role", Toast.LENGTH_SHORT).show()
                    preferenceManager.saveToken(token)
                    navigateBasedOnRole(role)
                }

                is ResultState.Error -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    Log.e("Login", "Login failed: ${response.exception}")
                    Toast.makeText(this, "Login failed: ${response.exception}", Toast.LENGTH_SHORT)
                        .show()
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