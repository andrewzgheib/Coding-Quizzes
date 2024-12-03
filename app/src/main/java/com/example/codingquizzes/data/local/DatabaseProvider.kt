package com.example.codingquizzes.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var db_instance: UserDatabase? = null

    fun getDatabase(context: Context): UserDatabase {
        return db_instance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "user_database"
            ).build()

            db_instance = instance
            instance
        }
    }
}