package com.example.codingquizzes.data.local

import android.content.Context
import android.util.Log
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var db_instance: QuizDatabase? = null

    fun getDatabase(context: Context): QuizDatabase {
        return db_instance ?: synchronized(this) {
            db_instance ?: createDatabase(context).also { db_instance = it }
        }
    }

    private fun createDatabase(context: Context): QuizDatabase {
        return try {
            Room.databaseBuilder(
                context.applicationContext,
                QuizDatabase::class.java,
                "quiz_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        } catch (e: Exception) {
            Log.e("DatabaseProvider", "Error creating database", e)

            context.deleteDatabase("quiz_database")
            Room.databaseBuilder(
                context.applicationContext,
                QuizDatabase::class.java,
                "quiz_database"
            ).build()
        }
    }
}