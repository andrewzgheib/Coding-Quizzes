package com.example.codingquizzes.quizzes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "quiz_id") var quizId: Int,
    @ColumnInfo(name = "question_type") var type: String,
    @ColumnInfo(name = "level") var level: String,
    @ColumnInfo(name = "question_text") var questionText: String,
    @ColumnInfo(name = "question_format") var questionType: String,
    @ColumnInfo(name = "first_option") var firstOption: String,
    @ColumnInfo(name = "second_option") var secondOption: String,
    @ColumnInfo(name = "third_option") var thirdOption: String,
    @ColumnInfo(name = "correct_answer") var correctAnswer: String
)
