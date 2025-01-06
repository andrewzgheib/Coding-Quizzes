package com.example.codingquizzes.quizzes.data.repository

import com.example.codingquizzes.quizzes.data.api.Resource
import com.example.codingquizzes.quizzes.data.local.UserAnswerDao
import com.example.codingquizzes.quizzes.data.model.UserAnswer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserAnswerRepository(
    private val userAnswerDao: UserAnswerDao
) {
    fun getAllUserAnswers(): Flow<Resource<List<UserAnswer>>> = flow {
        emit(Resource.Loading())
        userAnswerDao.getAllUserAnswers().collect { answers ->
            emit(Resource.Success(answers))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun updateUserAnswer(userAnswer: UserAnswer): Resource<Unit> {
        return try {
            userAnswerDao.updateUserAnswer(userAnswer)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to update user answer: ${e.message}")
        }
    }

    suspend fun getUserAnswerForQuestion(questionId: Int): Resource<UserAnswer?> {
        return try {
            val answer = userAnswerDao.getUserAnswerForQuestion(questionId)
            Resource.Success(answer)
        } catch (e: Exception) {
            Resource.Error("Failed to fetch user answer for question: ${e.message}")
        }
    }

    suspend fun insertUserAnswer(userAnswer: UserAnswer): Resource<Unit> {
        return try {
            userAnswerDao.insertUserAnswer(userAnswer)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to insert user answer: ${e.message}")
        }
    }

    suspend fun deleteAllUserAnswers(): Resource<Unit> {
        return try {
            userAnswerDao.deleteAllUserAnswers()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to delete all user answers: ${e.message}")
        }
    }
}
