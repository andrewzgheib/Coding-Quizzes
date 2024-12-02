package com.example.codingquizzes.quizzes.data.repository

import androidx.lifecycle.LiveData
import com.example.codingquizzes.quizzes.data.local.QuizListDao
import com.example.codingquizzes.quizzes.data.model.Quiz

class QuizListRepository(private val quizListDao: QuizListDao) {
    fun getAllQuizzes(): LiveData<List<Quiz>> = quizListDao.getQuizzes()

 /*   suspend fun insert(quiz: Quiz){
        quizListDao.insert(quiz)
    }*/

    suspend fun insertAll(quizList: List<Quiz>){
        quizListDao.insertAll(quizList)
    }

/*    suspend fun update(quiz: Quiz){
        quizListDao.update(quiz.id, quiz.title, quiz.description, quiz.prerequisite)
    }

    suspend fun delete(quiz: Quiz){
        quizListDao.delete(quiz)
    }*/
}