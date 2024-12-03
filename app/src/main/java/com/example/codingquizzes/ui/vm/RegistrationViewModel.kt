package com.example.codingquizzes.ui.vm

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationViewModel : ViewModel() {

    val reg_success = MutableLiveData<Boolean>()
    val error_message = MutableLiveData<String>()
    val reg_email_error = MutableLiveData<String?>()
    val reg_password_error = MutableLiveData<String?>()

    private val auth: FirebaseAuth = Firebase.auth

    fun register(email: String, password: String) {

        reg_email_error.value = null
        reg_password_error.value = null

        if (!isValidEmail(email)) {
            reg_email_error.value = "Please enter a valid email"
            return
        }

        if (password.length < 6) {
            reg_password_error.value = "Password must be at least 6 characters"
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    reg_success.value = true
                } else {
                    if (task.exception != null) {
                        error_message.value = task.exception?.message
                    } else {
                        error_message.value = "Registration Failed"
                    }
                }
            }
    }
    private fun isValidEmail(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}