package com.example.codingquizzes.quizzes.data.repository

import androidx.lifecycle.LiveData
import com.example.codingquizzes.quizzes.data.local.UserAnswerDao
import com.example.codingquizzes.quizzes.data.model.UserAnswer

class UserAnswerRepository(private val userAnswerDao: UserAnswerDao) {
    fun getAllUserAnswers(): LiveData<List<UserAnswer>> = userAnswerDao.getUserAnswers()

    suspend fun insertAnswer(userAnswer: UserAnswer) {
        userAnswerDao.insert(userAnswer)
    }

    suspend fun getAnswerForQuestion(questionId: Int): UserAnswer {
        return userAnswerDao.getAnswerForQuestion(questionId)
    }

    suspend fun updateAnswer(userAnswer: UserAnswer) {
        userAnswerDao.update(userAnswer)
    }
}
