package com.example.codingquizzes.quizzes.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.codingquizzes.R

class DifficultyLevelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_difficulty_level)

        val beginner: Button = findViewById(R.id.beginner_btn)
        val intermediate: Button = findViewById(R.id.intermediate_btn)
        val advanced: Button = findViewById(R.id.advanced_btn)

        val quizCategory = intent.getStringExtra("QUIZ_CATEGORY")

        beginner.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra("DIFFICULTY_LEVEL", "easy")
                putExtra("QUIZ_CATEGORY", quizCategory)
            }
            startActivity(intent)
        }

        intermediate.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra("DIFFICULTY_LEVEL", "medium")
                putExtra("QUIZ_CATEGORY", quizCategory)
            }
            startActivity(intent)
        }

        advanced.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra("DIFFICULTY_LEVEL", "medium")
                putExtra("QUIZ_CATEGORY", quizCategory)
            }
            startActivity(intent)
        }
    }
}
