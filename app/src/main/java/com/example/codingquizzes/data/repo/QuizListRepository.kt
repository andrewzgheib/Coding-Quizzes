package com.example.codingquizzes.data.repo

import com.example.codingquizzes.data.api.QuizApiService
import com.example.codingquizzes.data.api.Resource
import com.example.codingquizzes.data.model.Quiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuizListRepository(
    private val apiService: QuizApiService
) {
    fun getQuizzes(): Flow<Resource<List<Quiz>>> = flow {
        emit(Resource.Loading())
        try {
            val networkQuizzes = apiService.getQuizzes()

            val excludedCategories = listOf(
                "bash", "blockchain", "vuejs", "laravel", "kubernetes", "ruby",
                "devops", "openshift", "terraform", "cpanel", "ubuntu",
                "nodejs", "wordpress", "next.js", "apache kafka", "undefined", "django"
            )

            val filteredQuizzes = networkQuizzes.filter { quiz ->
                val category = quiz.category?.lowercase() ?: ""
                !excludedCategories.contains(category)
            }

            emit(Resource.Success(filteredQuizzes))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch quizzes: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)
        .catch { e -> emit(Resource.Error("Unexpected error: ${e.message}")) }
}
