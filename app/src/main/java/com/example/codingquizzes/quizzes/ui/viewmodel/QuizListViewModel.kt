package com.example.codingquizzes.quizzes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.codingquizzes.quizzes.data.api.Resource
import com.example.codingquizzes.quizzes.data.api.RetrofitInstance
import com.example.codingquizzes.quizzes.data.model.Quiz
import com.example.codingquizzes.quizzes.data.repository.QuizListRepository
import kotlinx.coroutines.launch

class QuizListViewModel(application: Application, quizListRepository: QuizListRepository? = null): AndroidViewModel(application) {
    constructor(application: Application) : this(application, null)

    private val _quizListRepository: QuizListRepository = quizListRepository ?: QuizListRepository(
        RetrofitInstance.RetrofitInstance.quizApiService
    )

    private val _networkQuizzes = MutableLiveData<Resource<List<Quiz>>>()
    val networkQuizzes: LiveData<Resource<List<Quiz>>> = _networkQuizzes

    init {
        fetchQuizzes()
    }

    private fun fetchQuizzes() {
        viewModelScope.launch {
            _quizListRepository.getQuizzes().collect { resource ->
                _networkQuizzes.value = resource
            }
        }
    }
}
