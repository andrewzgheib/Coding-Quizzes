package com.example.codingquizzes.ui.view.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.codingquizzes.R
import com.example.codingquizzes.ui.view.MainActivity
import com.example.codingquizzes.ui.vm.RegistrationViewModel

class RegistrationActivity: AppCompatActivity() {

    private lateinit var reg_email: EditText
    private lateinit var reg_password: EditText
    private lateinit var reg_btn: Button
    private lateinit var reg_link: TextView
    private lateinit var reg_email_error: TextView
    private lateinit var reg_password_error: TextView

    private lateinit var registerVM: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerVM = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        reg_email = findViewById(R.id.reg_email)
        reg_password = findViewById(R.id.reg_password)
        reg_btn = findViewById(R.id.reg_btn)
        reg_link = findViewById(R.id.reg_link)
        reg_email_error = findViewById(R.id.reg_email_error)
        reg_password_error = findViewById(R.id.reg_password_error)

        registerVM.reg_success.observe(this) { isSuccess ->
            if (isSuccess) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        registerVM.reg_email_error.observe(this) { error_message ->
            if (error_message != null) {
                reg_email_error.text = error_message
                reg_email_error.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    reg_email_error.visibility = View.GONE
                }, 2000)
            } else {
                reg_email_error.visibility = View.GONE
            }
        }

        registerVM.reg_password_error.observe(this) { error_message ->
            if (error_message != null) {
                reg_password_error.text = error_message
                reg_password_error.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    reg_password_error.visibility = View.GONE
                }, 2000)
            } else {
                reg_password_error.visibility = View.GONE
            }
        }

        reg_btn.setOnClickListener {
            val email = reg_email.text.toString().trim()
            val password = reg_password.text.toString().trim()

            registerVM.register(email, password)
        }

        reg_link.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}