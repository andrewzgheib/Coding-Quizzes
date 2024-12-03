package com.example.codingquizzes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prefs")
data class UserPref (
    @PrimaryKey val id: Int = 1,
    val bgColor: String
)