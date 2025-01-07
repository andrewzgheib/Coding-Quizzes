package com.example.codingquizzes.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.data.api.Resource
import com.example.codingquizzes.data.model.Question
import com.example.codingquizzes.data.model.QuestionWithAnswer
import com.example.codingquizzes.data.model.UserAnswer
import com.example.codingquizzes.ui.adapter.ReviewAnswerAdapter
import com.example.codingquizzes.ui.vm.UserAnswerViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReviewAnswerActivity : AppCompatActivity() {
    private lateinit var reviewAnswerAdapter: ReviewAnswerAdapter
    private var questions: List<Question> = listOf()
    private lateinit var userAnswerViewModel: UserAnswerViewModel
    private var userAnswers: List<UserAnswer> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.view_answers_layout)

        setupViewModel()

        userAnswerViewModel.allUserAnswers.observe(this, Observer { resource ->
            if (resource is Resource.Success) {
                userAnswers = resource.data ?: emptyList()

                val questionWithAnswers = combineQuestionsWithAnswers(questions, userAnswers)

                reviewAnswerAdapter.submitList(questionWithAnswers)
            }
        })

        val recyclerView = findViewById<RecyclerView>(R.id.view_answer_id)
        reviewAnswerAdapter = ReviewAnswerAdapter()
        setupRecyclerView(recyclerView, reviewAnswerAdapter)

        questions = loadQuestions()
        reviewAnswerAdapter.submitList(emptyList())

    }

    private fun setupViewModel() {
        val application = requireNotNull(application)
        userAnswerViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(
            UserAnswerViewModel::class.java)
        userAnswerViewModel.fetchAllUserAnswers()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadQuestions(): List<Question> {
        val sharedPrefs = getSharedPreferences("quiz_prefs", MODE_PRIVATE)
        val json = sharedPrefs.getString("questions", null)
        return if (json != null) {
            val type = object : TypeToken<List<Question>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun combineQuestionsWithAnswers(
        questions: List<Question>,
        userAnswers: List<UserAnswer>
    ): List<QuestionWithAnswer> {
        return questions.map { question ->
            val userAnswer = userAnswers.find { it.questionId == question.id }
            QuestionWithAnswer(question, userAnswer)
        }
    }

}
