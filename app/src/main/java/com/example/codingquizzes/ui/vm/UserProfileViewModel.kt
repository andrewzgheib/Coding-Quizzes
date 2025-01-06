package com.example.codingquizzes.ui.vm

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.codingquizzes.data.entities.User
import com.example.codingquizzes.data.local.AppDatabase
import com.example.codingquizzes.data.repo.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepo

    val currentUser: StateFlow<User?>
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val error_message = MutableLiveData<String>()
    val success_message = MutableLiveData<String>()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = UserRepo(
            database.userDao(),
            application.applicationContext
        )
        currentUser = repository.getCurrentUserFlow()
            .stateIn(viewModelScope, SharingStarted.Lazily, null)
    }

    fun updateProfile(
        username: String,
        bio: String?,
        profilePictureUri: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updateProfile(
                    username = username,
                    bio = bio,
                    profilePictureUri = profilePictureUri
                )
                success_message.value = "Changes saved"
            } catch (e: Exception) {
                error_message.value = "Error saving info"
                Log.e("UserProfileViewModel.updateProfile()", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun uploadProfilePicture(uri: Uri): String {
        return repository.saveProfilePictureLocally(uri)
    }
}