package com.example.codingquizzes.quizzes.data.local

import com.example.codingquizzes.quizzes.data.model.Quiz

object DummyData {
    fun listQuizzes(): List<Quiz> {
        val newQuizzes = mutableListOf(
            Quiz(1, "C# Knowledge", "Comprehensive test of C# programming.", "C#"),
            Quiz(2, "PostgreSQL Knowledge", "In-depth PostgreSQL database and SQL test.", "PostgreSQL"),
            Quiz(3, "MVVM Design", "Learn about the MVVM design pattern in software development.", "Design patterns"),
            Quiz(4, "Data Structures", "Test your understanding of data structures and algorithms.", "Data structures"),
            Quiz(5, "Tech Knowledge", "General quiz on various technology topics.", "Technology"),
            Quiz(6, "Unix Commands", "Test your knowledge of Unix commands and shell scripting.", "Unix"),
            Quiz(7, "Kotlin Knowledge", "Assess your understanding of Kotlin programming.", "Kotlin")
        )
        return newQuizzes
    }
}

