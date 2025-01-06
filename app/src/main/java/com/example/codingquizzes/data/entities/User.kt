package com.example.codingquizzes.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val firebaseUid: String,
    var username: String,
    var bio: String?,
    var profilePictureUri: String?
)