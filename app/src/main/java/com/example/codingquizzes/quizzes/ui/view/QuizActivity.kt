package com.example.codingquizzes.quizzes.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.data.api.Resource
import com.example.codingquizzes.quizzes.data.model.Question
import com.example.codingquizzes.quizzes.ui.adapter.QuestionSelectorAdapter
import com.example.codingquizzes.quizzes.ui.adapter.QuizPagerAdapter
import com.example.codingquizzes.quizzes.ui.viewmodel.QuestionViewModel

class QuizActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: QuestionViewModel
    private lateinit var quizPagerAdapter: QuizPagerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionSelectorAdapter: QuestionSelectorAdapter
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private var currentPosition = 0
    private var questions: List<Question> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        viewPager = findViewById(R.id.view_pager)
        progressBar = findViewById(R.id.progress_bar_question)
        recyclerView = findViewById(R.id.question_selector_recycler_view)
        previousButton = findViewById(R.id.previous_btn_question)
        nextButton = findViewById(R.id.next_btn_question)

        viewPager.isUserInputEnabled = false

        val quizCategory = intent.getStringExtra("QUIZ_CATEGORY") ?: "General Knowledge"
        val difficultyLevel = intent.getStringExtra("DIFFICULTY_LEVEL") ?: "easy"

        setupViewModel()
        observeData()

        viewModel.fetchQuestions(difficultyLevel.lowercase(), quizCategory)

        previousButton.setOnClickListener {
            navigateToPreviousFragment()
        }

        nextButton.setOnClickListener {
            navigateToNextFragment()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
    }

    private fun setupRecyclerView() {
        questionSelectorAdapter = QuestionSelectorAdapter(questions.size)
        recyclerView.adapter = questionSelectorAdapter
        recyclerView.layoutManager = GridLayoutManager(this, questions.size)
    }

    private fun observeData() {
        viewModel.networkQuestions.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    resource.data?.let { questions ->
                        this.questions = questions
                        setupViewPager(questions)
                        setupRecyclerView()
                    } ?: run {
                        Toast.makeText(this, "No questions available.", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Failed to load questions: ${resource.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupViewPager(questions: List<Question>) {
        quizPagerAdapter = QuizPagerAdapter(this, questions)
        viewPager.adapter = quizPagerAdapter
    }

    private fun navigateToPreviousFragment() {
        if (currentPosition > 0) {
            currentPosition--
            viewPager.setCurrentItem(currentPosition, true)
        }
        updateNextButtonText()
    }

    private fun navigateToNextFragment() {
        if (currentPosition < questions.size - 1) {
            currentPosition++
            viewPager.setCurrentItem(currentPosition, true)
        }
        updateNextButtonText()
    }

    private fun updateNextButtonText() {
        if (currentPosition == questions.size - 1) {
            nextButton.text = getString(R.string.submit_btn)
        } else {
            nextButton.text = getString(R.string.next_btn)
        }
    }
}
