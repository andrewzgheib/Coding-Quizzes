package com.example.codingquizzes.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.codingquizzes.R
import com.example.codingquizzes.data.model.Quiz
import com.example.codingquizzes.ui.view.DifficultyLevelActivity

class QuizzesAdapter : ListAdapter<Quiz, QuizzesAdapter.QuizzesViewHolder>(QuizDiffCallback()) {

    inner class QuizzesViewHolder(itemView: View) : ViewHolder(itemView) {
        val quizTitle: TextView = itemView.findViewById(R.id.quiz_title)
        val categoryIcon: ImageView = itemView.findViewById(R.id.categoryIcon)

        init {
            itemView.setOnClickListener {
                val quiz = getItem(adapterPosition)
                val context = itemView.context

                val intent = Intent(context, DifficultyLevelActivity::class.java).apply {
                    putExtra("QUIZ_CATEGORY", quiz.category)
                }
                context.startActivity(intent)
            }
        }
    }

    class QuizDiffCallback : DiffUtil.ItemCallback<Quiz>() {
        override fun areItemsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizzesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.quiz_list_rv_row_layout, parent, false)
        return QuizzesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuizzesViewHolder, position: Int) {
        val quiz = getItem(position)

        holder.quizTitle.text = if (quiz.category.equals("css", ignoreCase = true)) {
            quiz.category?.uppercase()
        } else {
            quiz.category
        }

        val iconResId = getCategoryIcon(quiz.category)
        holder.categoryIcon.setImageResource(iconResId)

    }

    private fun getCategoryIcon(category: String?): Int {
        return when (category?.lowercase()) {
            "html" -> R.drawable.ic_html
            "css" -> R.drawable.ic_css
            "angular" -> R.drawable.ic_angular
            "react"-> R.drawable.ic_react
            "javascript"-> R.drawable.ic_javascript
            ".net" -> R.drawable.ic_dotnet
            "php" -> R.drawable.ic_php
            "postgres" -> R.drawable.ic_postgresql
            "mysql" -> R.drawable.ic_mysql
            "docker" -> R.drawable.ic_docker
            "aws" -> R.drawable.ic_aws
            "git" -> R.drawable.ic_git
            "c" -> R.drawable.ic_c
            "java" -> R.drawable.ic_java
            "swift" -> R.drawable.ic_swift
            "python" -> R.drawable.ic_python
            "linux" -> R.drawable.ic_linux
            "ai" -> R.drawable.ic_ai
            else -> R.drawable.ic_noimage
        }
    }
}
