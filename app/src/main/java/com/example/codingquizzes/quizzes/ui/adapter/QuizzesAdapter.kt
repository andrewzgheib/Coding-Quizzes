package com.example.codingquizzes.quizzes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.data.model.Quiz

class QuizzesAdapter(private var quizList: List<Quiz>) : RecyclerView.Adapter<QuizzesAdapter.QuizzesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizzesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_list_rv_row_layout, parent, false)
        return QuizzesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    override fun onBindViewHolder(holder: QuizzesViewHolder, position: Int) {
        val quiz = quizList[position]
        holder.quizTitle.text = quiz.title
        holder.quizDescription.text = quiz.description
        holder.quizPrerequisite.text = quiz.prerequisite
    }

    fun updateQuizzes(newQuizzes: List<Quiz>) {
        val previousSize = quizList.size
        quizList = newQuizzes
        notifyItemRangeInserted(previousSize, newQuizzes.size)
    }

    class QuizzesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quizTitle: TextView = itemView.findViewById(R.id.quiz_title)
        val quizDescription: TextView = itemView.findViewById(R.id.quiz_description)
        val quizPrerequisite: TextView = itemView.findViewById(R.id.prerequisite)
    }
}