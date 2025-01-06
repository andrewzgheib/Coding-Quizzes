package com.example.codingquizzes.quizzes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_answer")
data class UserAnswer(
    @ColumnInfo("question_id")
    @PrimaryKey val questionId: Int,
    @ColumnInfo("selected_answer")
    val selectedAnswer: String?
)
