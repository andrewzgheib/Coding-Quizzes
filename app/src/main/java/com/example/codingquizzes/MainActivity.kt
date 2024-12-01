package com.example.codingquizzes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.quizzes.Quiz
import com.example.codingquizzes.quizzes.QuizzesAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_quizzes)
        val quizList = generateQuizList()
        val quizzesAdapter = QuizzesAdapter(quizList)
        recyclerView.adapter = quizzesAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun generateQuizList() : ArrayList<Quiz>{
        val list:ArrayList<Quiz> = ArrayList<Quiz>()

        for(i in 0..10){
            list.add(Quiz("Quiz" + i, "Description" + i,"Prerequisite" + i))
        }

        return list
    }
}