package com.example.codingquizzes.data.entities

data class SubmissionRequest(
    val source_code: String,
    val language_id: Int,
    val stdin: String = ""
)