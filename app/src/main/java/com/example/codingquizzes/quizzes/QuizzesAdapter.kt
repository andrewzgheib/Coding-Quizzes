package com.example.codingquizzes.quizzes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R

class QuizzesAdapter(private val quizList:ArrayList<Quiz>):
RecyclerView.Adapter<QuizzesAdapter.QuizzesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizzesViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_list_rv_row_layout, parent, false)

        return QuizzesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  quizList.size
    }

    override fun onBindViewHolder(holder: QuizzesViewHolder, position: Int) {
        val quiz = quizList[position]
        holder.quizTitle.text = quiz.title
        holder.quizDescription.text = quiz.description
        holder.quizPrerequisite.text = quiz.prerequisite
    }

    class QuizzesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val quizTitle: TextView = itemView.findViewById(R.id.quiz_title)
        val quizDescription: TextView = itemView.findViewById(R.id.quiz_description)
        val quizPrerequisite: TextView = itemView.findViewById(R.id.prerequisite)
    }
}