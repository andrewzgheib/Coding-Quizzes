package com.example.codingquizzes.quizzes.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.ui.adapter.QuizzesAdapter
import com.example.codingquizzes.quizzes.ui.viewmodel.QuizListViewModel

class MainActivity : AppCompatActivity() {
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

        quizzesAdapter = QuizzesAdapter(emptyList())
        recyclerView.adapter = quizzesAdapter

        viewModel = ViewModelProvider(this)[QuizListViewModel::class.java]

        viewModel.allQuizzes.observe(this) { quizzes ->
            quizzesAdapter.updateQuizzes(quizzes)
        }

        viewModel.insertAllQuizzes()
        viewModel.refreshQuizzes()
    }
}
