package com.example.codingquizzes.quizzes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_answer")
data class UserAnswer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "question_id")
    val questionId: Int,
    @ColumnInfo(name = "selected_answer")
    val selectedAnswer: String
)
