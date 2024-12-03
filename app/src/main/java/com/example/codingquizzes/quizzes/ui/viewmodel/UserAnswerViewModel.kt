package com.example.codingquizzes.quizzes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.codingquizzes.quizzes.data.local.DatabaseProvider
import com.example.codingquizzes.quizzes.data.model.UserAnswer
import com.example.codingquizzes.quizzes.data.repository.UserAnswerRepository
import kotlinx.coroutines.launch

class UserAnswerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserAnswerRepository
    val allUserAnswers: LiveData<List<UserAnswer>>

    init {
        val userAnswersDao = DatabaseProvider.getDatabase(application).userAnswerDao()
        repository = UserAnswerRepository(userAnswersDao)
        allUserAnswers = repository.getAllUserAnswers()
    }

    fun refreshUserAnswers() = viewModelScope.launch {
        allUserAnswers
    }

    fun insertUserAnswer(userAnswer: UserAnswer) = viewModelScope.launch {
        repository.insertAnswer(userAnswer)
    }

    fun getAnswerForQuestion(questionId: Int) = viewModelScope.launch {
        repository.getAnswerForQuestion(questionId)
    }

    fun updateUserAnswer(userAnswer: UserAnswer) = viewModelScope.launch {
        repository.updateAnswer(userAnswer)
    }
}
