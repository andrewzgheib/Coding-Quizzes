package com.example.codingquizzes.quizzes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.data.model.Quiz

class QuizzesSection6Adapter :  ListAdapter<Quiz, QuizzesSection6Adapter.QuizzesViewHolder>(QuizDiffCallback()) {

    inner class QuizzesViewHolder(itemView: View) : ViewHolder(itemView) {
        val quizTitle: TextView = itemView.findViewById(R.id.quiz_title)
        val categoryIcon: ImageView = itemView.findViewById(R.id.categoryIcon) // Reference to ImageView
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

        holder.quizTitle.text = quiz.category

        val iconResId = getCategoryIcon(quiz.category)
        holder.categoryIcon.setImageResource(iconResId)
    }

    private fun getCategoryIcon(category: String?): Int {
        return when (category?.lowercase()) {
            "linux" -> R.drawable.ic_linux
            "ai" -> R.drawable.ic_ai
            else -> R.drawable.ic_noimage
        }
    }
}
