package com.example.codingquizzes.quizzes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codingquizzes.quizzes.data.model.Quiz

@Database(entities = [Quiz::class], version = 1, exportSchema = false)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizListDao(): QuizListDao
}