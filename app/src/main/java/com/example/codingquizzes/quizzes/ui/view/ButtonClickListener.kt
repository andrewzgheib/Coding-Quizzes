package com.example.codingquizzes.quizzes.ui.view

import com.example.codingquizzes.quizzes.data.model.Quiz

interface ButtonClickListener {
    fun itemClicked(quiz: Quiz)
}