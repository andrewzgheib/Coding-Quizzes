package com.example.codingquizzes.quizzes.data.repository

import com.example.codingquizzes.quizzes.data.api.QuestionApiService
import com.example.codingquizzes.quizzes.data.api.Resource
import com.example.codingquizzes.quizzes.data.model.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuestionRepository(
    private val apiService: QuestionApiService
) {
    fun getQuestions(
        difficulty: String,
        category: String? = null
    ): Flow<Resource<List<Question>>> = flow {
        emit(Resource.Loading())
        try {
            val tag = when (category?.lowercase()) {
                "html" -> "HTML"
                "css" -> "CSS"
                "javascript" -> "JavaScript"
                "react" -> "React"
                "angular" -> "Angular"
                ".net" -> ".NET"
                "php" -> "PHP"
                "postgres" -> "PostgreSQL"
                "mysql" -> "MySQL"
                "git" -> "Git"
                "docker" -> "Docker"
                "aws" -> "AWS"
                "python" -> "Python"
                "java" -> "Java"
                "c" -> "C"
                "swift" -> "Swift"
                "ai" -> "AI"
                "linux" -> "Linux"
                else -> category
            }

            val response = apiService.getQuestions(
                difficulty = difficulty,
                tags = tag,
                limit = 10
            )

            if (response.isSuccessful) {
                val questions = response.body() ?: emptyList()

                val filteredQuestions = questions.filter { question ->
                    question.tags.any { it.name.equals(tag, ignoreCase = true) }
                }

                if (filteredQuestions.isEmpty()) {
                    emit(Resource.Error("No questions found for category: $category"))
                } else {
                    emit(Resource.Success(filteredQuestions))
                }
            } else {
                emit(Resource.Error("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch questions: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)
        .catch { e: Throwable ->
            emit(Resource.Error("Unexpected Error: ${e.message}"))
        }
}