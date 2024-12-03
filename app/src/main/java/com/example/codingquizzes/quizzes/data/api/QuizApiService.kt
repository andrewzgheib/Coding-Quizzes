package com.example.codingquizzes.quizzes.data.api

import com.example.codingquizzes.quizzes.data.model.Quiz
import retrofit2.http.GET

interface QuizApiService {
    @GET("quizzes/quizzes")
    suspend fun fetchQuizzes(): List<Quiz>
}
