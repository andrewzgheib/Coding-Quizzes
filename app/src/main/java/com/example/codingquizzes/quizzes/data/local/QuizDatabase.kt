package com.example.codingquizzes.quizzes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.codingquizzes.quizzes.data.model.UserAnswer

@Database(entities = [UserAnswer::class], version = 10, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun userAnswerDao(): UserAnswerDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getDatabase(context: Context): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                )
                    // Use destructive migration if there's an issue with the table
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
