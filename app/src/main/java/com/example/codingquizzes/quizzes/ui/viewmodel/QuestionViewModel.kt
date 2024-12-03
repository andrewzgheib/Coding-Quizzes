package com.example.codingquizzes.quizzes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.codingquizzes.quizzes.data.local.DatabaseProvider
import com.example.codingquizzes.quizzes.data.local.DummyQuestionData
import com.example.codingquizzes.quizzes.data.model.Question
import com.example.codingquizzes.quizzes.data.repository.QuestionRepository
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: QuestionRepository
    private val _allQuestions = MutableLiveData<List<Question>>()
    val allQuestions: LiveData<List<Question>> = _allQuestions

    init {
        val questionDao = DatabaseProvider.getDatabase(application).questionDao()
        repository = QuestionRepository(questionDao)
    }

    fun getQuestions(quizId: Int, level: String) {
        viewModelScope.launch {
            val questions = repository.getAllQuestions(quizId, level)
            questions.observeForever { questionList ->
                _allQuestions.value = questionList
            }
        }
    }

    fun insertAllQuestions(quizId: Int, level: String) = viewModelScope.launch {
        val newQuestions = DummyQuestionData.generateQuestions(quizId, level)
        repository.insertAll(newQuestions)
    }
}