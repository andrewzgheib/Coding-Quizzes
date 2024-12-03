package com.example.codingquizzes.quizzes.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://674f0c1abb559617b26dd0f7.mockapi.io/api/"

    val api: QuizApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApiService::class.java)
    }
}
