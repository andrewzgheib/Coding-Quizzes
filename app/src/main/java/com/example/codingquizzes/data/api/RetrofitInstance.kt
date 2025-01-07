package com.example.codingquizzes.data.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://quizapi.io"
    private const val API_KEY = "api_key"
    private const val API_KEY_HEADER = "X-Api-Key"
    object RetrofitInstance {
        private val gson: Gson = GsonBuilder()
            .create()

        private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createOkHttpClient())
            .build()

        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val requestWithApiKey = originalRequest.newBuilder()
                        .header(API_KEY_HEADER, API_KEY)
                        .build()
                    chain.proceed(requestWithApiKey)
                }
                .build()
        }

        val quizApiService: QuizApiService = retrofit.create(QuizApiService::class.java)
        val questionApiService: QuestionApiService = retrofit.create(QuestionApiService::class.java)
    }
}