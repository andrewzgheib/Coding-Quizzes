/*
package com.example.codingquizzes.quizzes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.data.model.Question

class QuestionsAdapter(
    private var questionList: List<Question>,
) : RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.mcq_rv_layout, parent, false)
        return QuestionsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val question = questionList[position]
        holder.text.text = question.questionText
        holder.firstOption.text = question.firstOption
        holder.secondOption.text = question.secondOption
        holder.thirdOption.text = question.thirdOption
    }

    fun updateQuestions(newQuizzes: List<Question>) {
        val previousSize = questionList.size
        questionList = newQuizzes
        notifyItemRangeInserted(previousSize, newQuizzes.size)
    }

    class QuestionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.question_text)
        val firstOption: RadioButton = itemView.findViewById(R.id.option_a)
        val secondOption: RadioButton = itemView.findViewById(R.id.option_b)
        val thirdOption: RadioButton = itemView.findViewById(R.id.option_c)
    }
}
*/


package com.example.codingquizzes.quizzes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.data.model.Question

class QuestionsAdapter(
    private var questions: List<Question>
) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    private val userAnswers = mutableMapOf<Int, String>()

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.question_text)
        val radioGroup: RadioGroup = itemView.findViewById(R.id.options_radio_group)
        val optionA: RadioButton = itemView.findViewById(R.id.option_a)
        val optionB: RadioButton = itemView.findViewById(R.id.option_b)
        val optionC: RadioButton = itemView.findViewById(R.id.option_c)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mcq_rv_layout, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.questionText.text = question.questionText
        holder.optionA.text = question.firstOption
        holder.optionB.text = question.secondOption
        holder.optionC.text = question.thirdOption

        holder.radioGroup.setOnCheckedChangeListener(null)
        when (userAnswers[question.id]) {
            question.firstOption -> holder.radioGroup.check(R.id.option_a)
            question.secondOption -> holder.radioGroup.check(R.id.option_b)
            question.thirdOption -> holder.radioGroup.check(R.id.option_c)
            else -> holder.radioGroup.clearCheck()
        }

        holder.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedAnswer = when (checkedId) {
                R.id.option_a -> question.firstOption
                R.id.option_b -> question.secondOption
                R.id.option_c -> question.thirdOption
                else -> null
            }
            if (selectedAnswer != null) {
                userAnswers[question.id] = selectedAnswer
            }
        }
    }

    override fun getItemCount(): Int = questions.size

    fun updateQuestions(newQuestions: List<Question>) {
        questions = newQuestions
        notifyDataSetChanged()
    }

    fun getUserAnswers(): Map<Int, String> {
        return userAnswers
    }
}
