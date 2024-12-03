package com.example.codingquizzes.quizzes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.codingquizzes.quizzes.data.model.Question
import com.example.codingquizzes.quizzes.data.model.Quiz
import com.example.codingquizzes.quizzes.data.model.UserAnswer

@Database(
    entities = [Quiz::class,Question::class, UserAnswer::class],
    version = 5, // Increment version number
    exportSchema = false
)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizListDao(): QuizListDao
    abstract fun questionDao(): QuestionDao
    abstract fun userAnswerDao(): UserAnswerDao

    companion object {
        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        fun getDatabase(context: Context): QuizDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                QuizDatabase::class.java,
                "quiz_database"
            )
                .addMigrations(MIGRATION_4_5)
                .build()
        }
    }
}