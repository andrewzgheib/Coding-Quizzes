package com.example.codingquizzes.quizzes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codingquizzes.quizzes.data.model.Question
import com.example.codingquizzes.quizzes.data.model.UserAnswer

@Database(
    entities = [Question::class, UserAnswer::class],
    version = 10,
    exportSchema = false
)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun userAnswerDao(): UserAnswerDao

    /*companion object {
        fun getDatabase(context: Context): QuizDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                QuizDatabase::class.java,
                "quiz_database"
            )
                .build()
        }
    }*/
}