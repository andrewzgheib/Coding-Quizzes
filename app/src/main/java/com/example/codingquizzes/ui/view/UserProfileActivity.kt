package com.example.codingquizzes.ui.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.codingquizzes.R
import com.example.codingquizzes.ui.vm.UserProfileViewModel

class UserProfileActivity : AppCompatActivity() {

    private lateinit var userProfile_name: EditText
    private lateinit var userProfile_username: EditText
    private lateinit var profile_btn: Button
    private lateinit var profile_message: TextView

    private val profileViewModel: UserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userProfile_name = findViewById(R.id.profile_name)
        userProfile_username = findViewById(R.id.profile_username)
        profile_btn = findViewById(R.id.profile_btn)
        profile_message = findViewById(R.id.profile_message)

        profile_btn.setOnClickListener {
            profileViewModel.saveProfileToFirebase()
        }

        profileViewModel.fetchUserProfiles()

        profileViewModel.username.observe(this) { updatedUsername ->
            userProfile_username.setText(updatedUsername)
        }

        profileViewModel.name.observe(this) { updatedName ->
            userProfile_name.setText(updatedName)
        }

        profileViewModel.profile_message.observe(this) { message ->
            if (message != null && message.isNotEmpty()) {
                profile_message.text = message
                profile_message.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    profile_message.visibility = View.GONE
                }, 2000)
            }
        }
    }
}
