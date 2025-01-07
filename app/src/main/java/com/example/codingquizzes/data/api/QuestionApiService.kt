package com.example.codingquizzes.data.api

import com.example.codingquizzes.data.model.Question
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionApiService {
    @GET("api/v1/questions")
    suspend fun getQuestions(
        @Query("difficulty") difficulty: String,
        @Query("tags") tags: String? = null,
        @Query("limit") limit: Int = 10
    ): Response<List<Question>>
}
