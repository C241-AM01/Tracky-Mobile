package com.megalogic.tracky.ui.role

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.megalogic.tracky.R
import android.widget.Button
import com.megalogic.tracky.ui.admin.AdminMainActivity
import com.megalogic.tracky.ui.pic.PICMainActivity
import com.megalogic.tracky.ui.user.UserMainActivity

class RoleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_role)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        val btnToUser = findViewById<Button>(R.id.btn_toUser)
        val btnToAdmin = findViewById<Button>(R.id.btn_toAdmin)
        val btnToPIC = findViewById<Button>(R.id.btn_toPIC)

        btnToUser.setOnClickListener {
            val intent = Intent(this, UserMainActivity::class.java)
            startActivity(intent)
        }

        btnToAdmin.setOnClickListener {
            val intent = Intent(this, AdminMainActivity::class.java)
            startActivity(intent)
        }

        btnToPIC.setOnClickListener {
            val intent = Intent(this, PICMainActivity::class.java)
            startActivity(intent)
        }
    }
}
