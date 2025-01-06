package com.example.codingquizzes.quizzes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.codingquizzes.quizzes.data.api.Resource
import com.example.codingquizzes.quizzes.data.local.DatabaseProvider
import com.example.codingquizzes.quizzes.data.model.UserAnswer
import com.example.codingquizzes.quizzes.data.repository.UserAnswerRepository
import kotlinx.coroutines.launch

class UserAnswerViewModel(application: Application, userAnswerRepository: UserAnswerRepository? = null) :
    AndroidViewModel(application) {

    constructor(application: Application) : this(application, null)

    private val _userAnswerRepository: UserAnswerRepository =
        userAnswerRepository ?: UserAnswerRepository(
            DatabaseProvider.getDatabase(application).userAnswerDao()
        )

    private val _allUserAnswers = MutableLiveData<Resource<List<UserAnswer>>>()
    val allUserAnswers: LiveData<Resource<List<UserAnswer>>> = _allUserAnswers

    fun fetchAllUserAnswers() {
        viewModelScope.launch {
            _userAnswerRepository.getAllUserAnswers().collect { resource ->
                _allUserAnswers.value = resource
            }
        }
    }

    fun insertUserAnswer(userAnswer: UserAnswer) {
        viewModelScope.launch {
            _userAnswerRepository.insertUserAnswer(userAnswer)
        }
    }

    fun getUserAnswerForQuestion(questionId: Int): LiveData<Resource<UserAnswer?>> {
        val liveData = MutableLiveData<Resource<UserAnswer?>>()
        viewModelScope.launch {
            val resource = _userAnswerRepository.getUserAnswerForQuestion(questionId)
            liveData.postValue(resource)
        }
        return liveData
    }

    fun deleteAllUserAnswers() {
        viewModelScope.launch {
            _userAnswerRepository.deleteAllUserAnswers()
        }
    }
}
