package com.example.codingquizzes.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.codingquizzes.R
import com.example.codingquizzes.ui.vm.UserAnswerViewModel

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_layout)

        val score = intent.getIntExtra("EXTRA_SCORE", 0)
        val totalQuestions = intent.getIntExtra("EXTRA_TOTAL_QUESTIONS", 0)
        val quizCategory = intent.getStringExtra("QUIZ_CATEGORY") ?: "General Knowledge"
        val difficultyLevel = intent.getStringExtra("DIFFICULTY_LEVEL") ?: "medium"

        val scoreTextView: TextView = findViewById(R.id.score_text_id)
        val correctAnswersTextView: TextView = findViewById(R.id.question_correct_id)
        val homeButton: Button = findViewById(R.id.home_btn)
        val retakeQuizButton: Button = findViewById(R.id.retake_quiz_btn)
        val reviewAnswersButton: Button = findViewById(R.id.review_answers_btn)

        val scorePercentage = (score.toDouble() / totalQuestions.toDouble()) * 100
        val formattedScore = "%.2f".format(scorePercentage)

        scoreTextView.text = getString(R.string.score_percentage, formattedScore)
        correctAnswersTextView.text = getString(R.string.correct_answers, score, totalQuestions)


        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        retakeQuizButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra("DIFFICULTY_LEVEL", difficultyLevel)
                putExtra("QUIZ_CATEGORY", quizCategory)
            }
            val userAnswerViewModel = ViewModelProvider(this)[UserAnswerViewModel::class.java]
            userAnswerViewModel.deleteAllUserAnswers()

            startActivity(intent)
        }

        reviewAnswersButton.setOnClickListener {
            val intent = Intent(this, ReviewAnswerActivity::class.java)
            startActivity(intent)

        }
    }
}
