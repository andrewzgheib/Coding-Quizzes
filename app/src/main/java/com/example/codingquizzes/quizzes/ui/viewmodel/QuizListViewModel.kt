package com.example.codingquizzes.quizzes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.codingquizzes.quizzes.data.local.DatabaseProvider
import com.example.codingquizzes.quizzes.data.model.Quiz
import com.example.codingquizzes.quizzes.data.repository.QuizListRepository
import kotlinx.coroutines.launch

class QuizListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: QuizListRepository
    val allQuizzes: LiveData<List<Quiz>>

    init {
        val quizListDao = DatabaseProvider.getDatabase(application).quizListDao()
        repository = QuizListRepository(quizListDao)
        allQuizzes = repository.getAllQuizzes()
    }

    fun fetchAndInsertQuizzes() = viewModelScope.launch {
        repository.fetchAndInsertQuizzes()
    }
}

