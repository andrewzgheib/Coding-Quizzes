package com.example.codingquizzes.data.entities

data class SubmissionResponse(
    val token: String? = null,
    val stdout: String? = null,
    val stderr: String? = null,
    val compile_output: String? = null,
    val message: String? = null,
    val status: Status? = null
)