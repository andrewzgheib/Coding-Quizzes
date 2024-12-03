package com.example.codingquizzes.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel: ViewModel() {

    val login_success = MutableLiveData<Boolean>()
    val error_message = MutableLiveData<String>()

    private val auth: FirebaseAuth = Firebase.auth

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            error_message.value = "Please enter email and password"
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    login_success.value = true
                } else {
                    error_message.value = "Login failed"
                }
            }
    }
}