package com.example.codingquizzes.quizzes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.codingquizzes.quizzes.data.api.Resource
import com.example.codingquizzes.quizzes.data.api.RetrofitInstance
import com.example.codingquizzes.quizzes.data.model.Question
import com.example.codingquizzes.quizzes.data.repository.QuestionRepository
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application, questionRepository: QuestionRepository? = null): AndroidViewModel(application) {
    constructor(application: Application) : this(application, null)

    private val _questionRepository: QuestionRepository = questionRepository ?: QuestionRepository(
        RetrofitInstance.RetrofitInstance.questionApiService
    )

    private val _networkQuestions = MutableLiveData<Resource<List<Question>>>()
    val networkQuestions: LiveData<Resource<List<Question>>> = _networkQuestions

    fun fetchQuestions(
        difficulty: String,
        tags: String? = null
    ) {
        viewModelScope.launch {
            _questionRepository.getQuestions(difficulty, tags).collect { resource ->
                _networkQuestions.value = resource
            }
        }
    }
}