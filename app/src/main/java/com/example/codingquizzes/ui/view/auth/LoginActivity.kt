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
import com.example.codingquizzes.ui.vm.LoginViewModel

class LoginActivity: AppCompatActivity() {

    private lateinit var login_email: EditText
    private lateinit var login_password: EditText
    private lateinit var login_btn: Button
    private lateinit var login_link: TextView
    private lateinit var login_error: TextView

    private lateinit var loginVM: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loginVM = ViewModelProvider(this).get(LoginViewModel::class.java)

        login_email = findViewById(R.id.login_email)
        login_password = findViewById(R.id.login_password)
        login_btn = findViewById(R.id.login_btn)
        login_link = findViewById(R.id.login_link)
        login_error = findViewById(R.id.login_error)

        loginVM.login_success.observe(this) { isSuccess ->
            if (isSuccess) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        loginVM.error_message.observe(this) { error_message ->
            if (error_message != null) {
                login_error.text = error_message
                login_error.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    login_error.visibility = View.GONE
                }, 2000)
            } else {
                login_error.visibility = View.GONE
            }
        }

        login_btn.setOnClickListener {
            val email = login_email.text.toString().trim()
            val password = login_password.text.toString().trim()

            loginVM.login(email, password)
        }

        login_link.setOnClickListener {
            intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}