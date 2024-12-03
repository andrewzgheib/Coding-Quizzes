package com.example.codingquizzes.quizzes.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.data.model.Quiz
import com.example.codingquizzes.quizzes.ui.adapter.QuizzesAdapter
import com.example.codingquizzes.quizzes.ui.viewmodel.QuizListViewModel

class MainActivity : AppCompatActivity(), ButtonClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var quizzesAdapter: QuizzesAdapter
    private lateinit var viewModel: QuizListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recycler_view_quizzes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        quizzesAdapter = QuizzesAdapter(emptyList(), this)
        recyclerView.adapter = quizzesAdapter

       viewModel = ViewModelProvider(this)[QuizListViewModel::class.java]

        viewModel.allQuizzes.observe(this) { quizzes ->
            quizzesAdapter.updateQuizzes(quizzes)
        }
        viewModel.fetchAndInsertQuizzes()
       // viewModel.insertAllQuizzes()
      //  viewModel.refreshQuizzes()
    }

    override fun itemClicked(quiz: Quiz) {
        val intent = Intent(this, DifficultyLevelActivity::class.java).apply {
            putExtra("QUIZ_ID", quiz.id)
        }
        startActivity(intent)
    }
}