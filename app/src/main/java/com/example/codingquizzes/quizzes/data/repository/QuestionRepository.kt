package com.example.codingquizzes.quizzes.data.repository

import androidx.lifecycle.LiveData
import com.example.codingquizzes.quizzes.data.local.QuestionDao
import com.example.codingquizzes.quizzes.data.model.Question

class QuestionRepository(private val questionDao: QuestionDao)
{
    fun getAllQuestions(quizId: Int, level: String): LiveData<List<Question>> =
        questionDao.getQuestionsByQuizAndLevel(quizId, level)

    suspend fun insertAll(questionList: List<Question>) {
        questionDao.insertAll(questionList)
    }

}
