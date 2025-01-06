package com.example.codingquizzes.data.repo

import com.example.codingquizzes.data.api.ApiService
import com.example.codingquizzes.data.entities.SubmissionRequest
import com.example.codingquizzes.data.entities.SubmissionResponse

class CodeRepo(private val apiService: ApiService) {
    suspend fun submitCode(code: String, languageId: Int, input: String = ""): Result<SubmissionResponse> {
        return try {
            val request = SubmissionRequest(code, languageId, input)
            val response = apiService.createSubmission(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("Failed to submit code"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSubmissionResult(token: String): Result<SubmissionResponse> {
        return try {
            val response = apiService.getSubmission(token)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception("Failed to get submission"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}