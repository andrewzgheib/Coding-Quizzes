package com.example.codingquizzes.data.api

import com.example.codingquizzes.data.model.Quiz
import retrofit2.http.GET

interface QuizApiService {
    @GET("api/v1/tags")
    suspend fun getQuizzes(): List<Quiz>
}
