package com.example.codingquizzes.data.repo

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.codingquizzes.data.entities.User
import com.example.codingquizzes.data.local.UserDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class UserRepo(
    private val userDao: UserDao,
    private val context: Context,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
) {
    private val currentUser get() = auth.currentUser

    fun getCurrentUserFlow(): Flow<User?> = currentUser?.uid?.let {
        userDao.getUserByUid(it)
    } ?: flow { emit(null) }

    suspend fun updateProfile(
        username: String,
        bio: String?,
        dateOfBirth: String?,
        profilePictureUri: String? = null
    ) = withContext(Dispatchers.IO) {
        val uid = currentUser?.uid ?: throw IllegalStateException("No user logged in")

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        try {
            currentUser?.updateProfile(profileUpdates)

            val user = User(
                firebaseUid = uid,
                username = username,
                bio = bio,
                dateOfBirth = dateOfBirth,
                profilePictureUri = profilePictureUri
            )

            userDao.insertUser(user)

            /*firestore.collection("users").document(uid)
                .set(user)
                .await()*/

        } catch (e: Exception) {
            Log.e("UserRepo.updateProfile", "Error: ${e.message}")
        }
    }

    suspend fun saveProfilePictureLocally(uri: Uri): String = withContext(Dispatchers.IO) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = "profile_picture_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        try {
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            return@withContext file.absolutePath
        } catch (e: IOException) {
            Log.e("UserRepo.saveProfilePictureLocally", "Error saving profile picture: ${e.message}")
            throw e
        }
    }
}