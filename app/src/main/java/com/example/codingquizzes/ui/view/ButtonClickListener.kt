package com.example.codingquizzes.ui.view

import com.example.codingquizzes.data.model.Quiz

interface ButtonClickListener {
    fun itemClicked(quiz: Quiz)
}