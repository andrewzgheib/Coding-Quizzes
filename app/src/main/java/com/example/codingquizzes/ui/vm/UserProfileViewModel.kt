package com.example.codingquizzes.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    val profile_message = MutableLiveData<String>()

    fun saveProfileToFirebase() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val user = hashMapOf(
                "name" to _name.value.orEmpty(),
                "username" to _username.value.orEmpty()
            )

            db.collection("users").document(currentUser.uid)
                .set(user)
                .addOnSuccessListener {
                    if (_name.value != null && _username.value != null) {
                        profile_message.value = "Profile Updated"
                    }

                }
                .addOnFailureListener { e ->
                    profile_message.value = "Error Saving Profile: ${e.message}"
                }
        } else {
            profile_message.value = "User not authenticated"
        }
    }

    fun fetchUserProfiles() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        _name.value = document.getString("name").orEmpty()
                        _username.value = document.getString("username").orEmpty()
                    } else {
                        profile_message.value = "No Profile Found"
                    }
                }
                .addOnFailureListener { exception ->
                    profile_message.value = "Error Fetching Profile: ${exception.message}"
                }
        }
    }
}
