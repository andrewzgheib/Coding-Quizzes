package com.example.codingquizzes.quizzes.ui.view

/*
class QuizActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private lateinit var viewModel: QuestionViewModel
    private lateinit var nextButton: Button
    private lateinit var title: TextView
    private var quizId: Int = 0
    private var level: String = "beginner"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        quizId = intent.getIntExtra("QUIZ_ID", 0)
        level = intent.getStringExtra("DIFFICULTY_LEVEL") ?: "beginner"

        recyclerView = findViewById(R.id.recycler_view_questions)
        recyclerView.layoutManager = LinearLayoutManager(this)

        questionsAdapter = QuestionsAdapter(emptyList())
        recyclerView.adapter = questionsAdapter

        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

        viewModel.allQuestions.observe(this) { questions ->
            if (questions.isNotEmpty()) {
                questionsAdapter.updateQuestions(questions)
            }
        }

        viewModel.insertAllQuestions(quizId, level)
        viewModel.getQuestions(quizId, level)

        title = findViewById(R.id.score_text_view)

        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            val userAnswers = questionsAdapter.getUserAnswers()
            val correctAnswers =
                viewModel.allQuestions.value?.associate { it.id to it.correctAnswer }

            if (correctAnswers != null) {
                var score = 0
                userAnswers.forEach { (questionId, userAnswer) ->
                    if (correctAnswers[questionId] == userAnswer) {
                        score++
                    }
                }
                val totalQuestions = correctAnswers.size
                title.text = "Score: $score/$totalQuestions"
            } else {
                title.text = "No questions loaded!"
            }
        }
    }
}*/
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.ui.adapter.QuestionsAdapter
import com.example.codingquizzes.quizzes.ui.viewmodel.QuestionViewModel

class QuizActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private lateinit var viewModel: QuestionViewModel
    private lateinit var nextButton: Button
    private lateinit var title: TextView
    private lateinit var timerTextView: TextView
    private var quizId: Int = 0
    private var level: String = "beginner"
    private var countDownTimer: CountDownTimer? = null
    private val quizDuration = 60_000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        quizId = intent.getIntExtra("QUIZ_ID", 0)
        level = intent.getStringExtra("DIFFICULTY_LEVEL") ?: "beginner"

        recyclerView = findViewById(R.id.recycler_view_questions)
        recyclerView.layoutManager = LinearLayoutManager(this)

        questionsAdapter = QuestionsAdapter(emptyList())
        recyclerView.adapter = questionsAdapter

        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

        viewModel.allQuestions.observe(this) { questions ->
            if (questions.isNotEmpty()) {
                questionsAdapter.updateQuestions(questions)
            }
        }

        viewModel.insertAllQuestions(quizId, level)
        viewModel.getQuestions(quizId, level)

        title = findViewById(R.id.score_text_view)
        timerTextView = findViewById(R.id.timer_text_view)

        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            calculateScore()
        }

        startQuizTimer()
    }

    private fun startQuizTimer() {
        countDownTimer = object : CountDownTimer(quizDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                timerTextView.text = "Time: $secondsRemaining s"
            }

            override fun onFinish() {
                timerTextView.text = "Time's up!"
                calculateScore()
                nextButton.isEnabled = false
            }
        }.start()
    }

    private fun calculateScore() {
        val userAnswers = questionsAdapter.getUserAnswers()
        val correctAnswers = viewModel.allQuestions.value?.associate { it.id to it.correctAnswer }

        if (correctAnswers != null) {
            val totalQuestions = correctAnswers.size
            var correctCount = 0
            userAnswers.forEach { (questionId, userAnswer) ->
                if (correctAnswers[questionId] == userAnswer) {
                    correctCount++
                }
            }
            val percentageScore = (correctCount.toDouble() / totalQuestions * 100).toInt()
            title.text = "Score: $percentageScore%"
        } else {
            title.text = "No questions loaded!"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
