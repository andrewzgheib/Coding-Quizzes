package com.example.codingquizzes.quizzes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import java.util.Locale

class QuestionSelectorAdapter(
    private val questionCount: Int
) : ListAdapter<Int, QuestionSelectorAdapter.QuestionSelectorViewHolder>(QuizDiffCallback()) {

    inner class QuestionSelectorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionNumber: TextView = itemView.findViewById(R.id.question_number)
    }

    class QuizDiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionSelectorViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.question_selector_rv_layout, parent, false)
        return QuestionSelectorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionSelectorViewHolder, position: Int) {
        val questionIndex = position + 1
        holder.questionNumber.text = String.format(Locale.getDefault(), "%d", questionIndex)
    }

    override fun getItemCount(): Int = questionCount
}