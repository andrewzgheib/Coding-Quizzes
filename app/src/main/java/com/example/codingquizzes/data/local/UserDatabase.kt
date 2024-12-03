package com.example.codingquizzes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codingquizzes.data.model.UserPref

@Database(entities = [UserPref::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userPrefDAO(): UserPrefDAO
}