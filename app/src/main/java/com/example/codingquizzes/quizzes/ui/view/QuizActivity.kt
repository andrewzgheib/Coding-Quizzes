package com.example.codingquizzes.quizzes.ui.view

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
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
import com.example.codingquizzes.quizzes.ui.viewmodel.UserAnswerViewModel
import com.google.gson.Gson
import java.util.Locale

class QuizActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var progressBar: ProgressBar
    private lateinit var questionViewModel: QuestionViewModel
    private lateinit var userAnswerViewModel: UserAnswerViewModel
    private lateinit var quizPagerAdapter: QuizPagerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionSelectorAdapter: QuestionSelectorAdapter
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private lateinit var timeTextView: TextView
    private var currentPosition = 0
    private var questions: List<Question> = listOf()
    private var totalQuizTime: Long = 0
    private var quizTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        deleteSavedUserAnswer()

        viewPager = findViewById(R.id.view_pager)
        progressBar = findViewById(R.id.progress_bar_question)
        recyclerView = findViewById(R.id.question_selector_recycler_view)
        previousButton = findViewById(R.id.previous_btn_question)
        nextButton = findViewById(R.id.next_btn_question)
        timeTextView = findViewById(R.id.timeTextView)

        viewPager.isUserInputEnabled = false

        val quizCategory = intent.getStringExtra("QUIZ_CATEGORY") ?: "General Knowledge"
        val difficultyLevel = intent.getStringExtra("DIFFICULTY_LEVEL") ?: "easy"

        setupViewModels()
        observeData()

        questionViewModel.fetchQuestions(difficultyLevel.lowercase(), quizCategory)

        previousButton.setOnClickListener {
            navigateToPreviousFragment()
        }

        nextButton.setOnClickListener {
            if (currentPosition == questions.size - 1) {
                stopTimer()
                calculateScore()
            } else {
                navigateToNextFragment()
            }
        }
    }
    private fun deleteSavedUserAnswer() {
        val userAnswerViewModel = ViewModelProvider(this)[UserAnswerViewModel::class.java]
        userAnswerViewModel.deleteAllUserAnswers()
    }
    private fun setupViewModels() {
        questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
        userAnswerViewModel = ViewModelProvider(this)[UserAnswerViewModel::class.java]
    }

    private fun setupRecyclerView() {
        questionSelectorAdapter = QuestionSelectorAdapter(questions.size)
        recyclerView.adapter = questionSelectorAdapter
        recyclerView.layoutManager = GridLayoutManager(this, questions.size)
    }

    private fun observeData() {
        questionViewModel.networkQuestions.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    resource.data?.let { questions ->
                        this.questions = questions
                        saveQuestions(questions)
                        setupViewPager(questions)
                        setupRecyclerView()
                        totalQuizTime = questions.size * 30000L
                        startQuizTimer()
                        if (questions.size == 1) {
                            nextButton.text = getString(R.string.submit_btn)
                        }

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

    private fun startQuizTimer() {
        quizTimer = object : CountDownTimer(totalQuizTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutesRemaining = (millisUntilFinished / 1000) / 60
                val secondsRemaining = (millisUntilFinished / 1000) % 60

                val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", minutesRemaining, secondsRemaining)

                val timeText = getString(R.string.time_left_format, formattedTime)

                timeTextView.text = timeText

                if (millisUntilFinished in 1..10000) {
                    val cardView = findViewById<CardView>(R.id.time_card)
                    cardView.backgroundTintList = ContextCompat.getColorStateList(this@QuizActivity, R.color.red)
                }

                if (millisUntilFinished in 9000..10000) {
                    playSound()

                }

            }

            override fun onFinish() {
                Toast.makeText(this@QuizActivity, "Quiz finished!", Toast.LENGTH_SHORT).show()
                stopTimer()
                calculateScore()
            }
        }

        quizTimer?.start()
    }

    private fun playSound() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.beepbeepbeep)
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
        }
    }

    private fun stopTimer() {
        quizTimer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    private fun calculateScore() {
        userAnswerViewModel.fetchAllUserAnswers()
        userAnswerViewModel.allUserAnswers.observe(this@QuizActivity) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    val userAnswers = resource.data
                    if (userAnswers.isNullOrEmpty()) {
                        Toast.makeText(this@QuizActivity, "No answers provided.", Toast.LENGTH_SHORT).show()
                        return@observe
                    }

                    var score = 0
                    var correctAnswers = 0

                    questions.forEach { question ->
                        val userAnswer = userAnswers.find { it.questionId == question.id }
                        if (userAnswer != null) {
                            val isCorrect = when (userAnswer.selectedAnswer) {
                                question.answers.answerA -> question.correctAnswers.answerAValid == "true"
                                question.answers.answerB -> question.correctAnswers.answerBValid == "true"
                                question.answers.answerC -> question.correctAnswers.answerCValid == "true"
                                question.answers.answerD -> question.correctAnswers.answerDValid == "true"
                                else -> false
                            }
                            if (isCorrect) {
                                score++
                                correctAnswers++
                            }
                        }
                    }

                    val diff = questions[0].difficulty
                    val category = questions[0].tags[0].name

                    val intent = Intent(this, ResultActivity::class.java).apply {
                        putExtra("EXTRA_SCORE", score)
                        putExtra("EXTRA_TOTAL_QUESTIONS", questions.size)
                        putExtra("QUIZ_CATEGORY", category)
                        putExtra("DIFFICULTY_LEVEL", diff)

                    }
                    startActivity(intent)
                    finish()
                }
                is Resource.Error -> {
                    Toast.makeText(this@QuizActivity, "Failed to load answers", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun saveQuestions(questions: List<Question>) {
        val sharedPrefs = getSharedPreferences("quiz_prefs", MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val json = Gson().toJson(questions)
        editor.putString("questions", json)
        editor.apply()
    }

}
