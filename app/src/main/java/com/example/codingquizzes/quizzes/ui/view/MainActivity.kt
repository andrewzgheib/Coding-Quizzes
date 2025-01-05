package com.example.codingquizzes.quizzes.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.data.api.Resource.Error
import com.example.codingquizzes.quizzes.data.api.Resource.Loading
import com.example.codingquizzes.quizzes.data.api.Resource.Success
import com.example.codingquizzes.quizzes.data.model.Quiz
import com.example.codingquizzes.quizzes.ui.adapter.QuizzesAdapter
import com.example.codingquizzes.quizzes.ui.viewmodel.QuizListViewModel

class MainActivity : AppCompatActivity(), ButtonClickListener {
    private lateinit var quizzesAdapter: QuizzesAdapter
    private lateinit var viewModel: QuizListViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var quizzesSection2Adapter: QuizzesAdapter
    private lateinit var quizzesSection3Adapter: QuizzesAdapter
    private lateinit var quizzesSection4Adapter: QuizzesAdapter
    private lateinit var quizzesSection5Adapter: QuizzesAdapter
    private lateinit var quizzesSection6Adapter: QuizzesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_section_1)
        val recyclerViewSection2 = findViewById<RecyclerView>(R.id.recycler_view_section_2)
        val recyclerViewSection3 = findViewById<RecyclerView>(R.id.recycler_view_section_3)
        val recyclerViewSection4 = findViewById<RecyclerView>(R.id.recycler_view_section_4)
        val recyclerViewSection5 = findViewById<RecyclerView>(R.id.recycler_view_section_5)
        val recyclerViewSection6 = findViewById<RecyclerView>(R.id.recycler_view_section_6)


        progressBar = findViewById(R.id.progressBar)
        setupViewModel()

        quizzesAdapter = QuizzesAdapter()
        quizzesSection2Adapter = QuizzesAdapter()
        quizzesSection3Adapter = QuizzesAdapter()
        quizzesSection4Adapter = QuizzesAdapter()
        quizzesSection5Adapter = QuizzesAdapter()
        quizzesSection6Adapter = QuizzesAdapter()

        setupRecyclerView(recyclerView, quizzesAdapter)
        setupRecyclerView(recyclerViewSection2, quizzesSection2Adapter)
        setupRecyclerView(recyclerViewSection3, quizzesSection3Adapter)
        setupRecyclerView(recyclerViewSection4, quizzesSection4Adapter)
        setupRecyclerView(recyclerViewSection5, quizzesSection5Adapter)
        setupRecyclerView(recyclerViewSection6, quizzesSection6Adapter)

        observeData()
    }

    private fun setupViewModel() {
        val application = requireNotNull(application)
        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(QuizListViewModel::class.java)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun observeData() {
        viewModel.networkQuizzes.observe(this) { resource ->
            when (resource) {
                is Loading -> {
                    progressBar.visibility = View.VISIBLE
                }

                is Success -> {
                    progressBar.visibility = View.GONE

                    resource.data?.let { networkNotes ->
                        val frontendQuizzes = networkNotes.filter { quiz ->
                            quiz.category in listOf("HTML", "Css", "JavaScript", "React", "Angular")
                        }.sortedBy { it.category?.lowercase() }

                        val backendQuizzes = networkNotes.filter { quiz ->
                            quiz.category in listOf(".Net", "PHP")
                        }.sortedBy { it.category?.lowercase() }

                        val sqlQuizzes = networkNotes.filter { quiz ->
                            quiz.category in listOf("Postgres", "MySQL")
                        }.sortedBy { it.category?.lowercase() }

                        val ciCdQuizzes = networkNotes.filter { quiz ->
                            quiz.category in listOf("Git", "Docker", "AWS")
                        }.sortedBy { it.category?.lowercase() }

                        val languagesQuizzes = networkNotes.filter { quiz ->
                            quiz.category in listOf("Python", "Java", "C", "Swift")
                        }.sortedBy { it.category?.lowercase() }

                        val othersQuizzes = networkNotes.filter { quiz ->
                            quiz.category in listOf("AI", "Linux")
                        }.sortedBy { it.category?.lowercase() }

                        quizzesAdapter.submitList(frontendQuizzes)
                        quizzesSection2Adapter.submitList(backendQuizzes)
                        quizzesSection3Adapter.submitList(sqlQuizzes)
                        quizzesSection4Adapter.submitList(ciCdQuizzes)
                        quizzesSection5Adapter.submitList(languagesQuizzes)
                        quizzesSection6Adapter.submitList(othersQuizzes)
                    }
                }

                is Error -> {
                    progressBar.visibility = View.GONE
                    AlertDialog.Builder(this)
                        .setMessage(resource.message)
                        .setPositiveButton(R.string.error_message) { dialogInterface, _ ->
                            dialogInterface.dismiss()
                        }
                        .show()
                }
            }
        }
    }

    override fun itemClicked(quiz: Quiz) {
        val intent = Intent(this, DifficultyLevelActivity::class.java).apply {
            putExtra("QUIZ_ID", quiz.id)
        }
        startActivity(intent)
    }
}