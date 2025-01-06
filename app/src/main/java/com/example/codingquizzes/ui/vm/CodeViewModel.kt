package com.example.codingquizzes.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingquizzes.data.api.ApiService
import com.example.codingquizzes.data.repo.CodeRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CodeViewModel : ViewModel() {
    private val repository: CodeRepo
    private val _executionResult = MutableLiveData<String>()
    val executionResult: LiveData<String> = _executionResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val API_KEY: String = "YOUR_API_KEY"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://judge0-ce.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        repository = CodeRepo(apiService)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-RapidAPI-Key", API_KEY)
                    .addHeader("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    fun executeCode(code: String, languageId: Int, input: String = "") {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val submissionResult = repository.submitCode(code, languageId, input)
                submissionResult.onSuccess { submission ->
                    submission.token?.let { token ->
                        var result = repository.getSubmissionResult(token).getOrNull()

                        while (result != null && (result.status?.id == 1 || result.status?.id == 2)) {
                            delay(1000)
                            result = repository.getSubmissionResult(token).getOrNull()
                        }

                        if (result != null) {
                            _executionResult.value = buildString {
                                append("Output:\n${result.stdout ?: ""}")
                                if (!result.stderr.isNullOrEmpty()) {
                                    append("\nErrors:\n${result.stderr}")
                                }
                                if (!result.compile_output.isNullOrEmpty()) {
                                    append("\nCompilation Output:\n${result.compile_output}")
                                }
                            }
                        } else {
                            _executionResult.value = "Error: Failed to get execution results"
                        }
                    }
                }.onFailure { exception ->
                    _executionResult.value = "Error: ${exception.message}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}