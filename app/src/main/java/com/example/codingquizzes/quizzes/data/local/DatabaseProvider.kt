package com.example.codingquizzes.quizzes.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var db_instance: QuizDatabase? = null

    fun getDatabase(context: Context): QuizDatabase {
        return db_instance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                QuizDatabase::class.java,
                "quiz_database"
            ).build()

            db_instance = instance
            instance
        }
    }
}