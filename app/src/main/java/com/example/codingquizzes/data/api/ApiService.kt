package com.example.codingquizzes.data.api

import com.example.codingquizzes.data.entities.SubmissionRequest
import com.example.codingquizzes.data.entities.SubmissionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("submissions")
    suspend fun createSubmission(@Body submission: SubmissionRequest): Response<SubmissionResponse>

    @GET("submissions/{token}")
    suspend fun getSubmission(@Path("token") token: String): Response<SubmissionResponse>
}