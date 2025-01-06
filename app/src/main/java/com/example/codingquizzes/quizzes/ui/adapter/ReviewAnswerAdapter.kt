package com.example.codingquizzes.quizzes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.data.model.Answers
import com.example.codingquizzes.quizzes.data.model.CorrectAnswers
import com.example.codingquizzes.quizzes.data.model.QuestionWithAnswer

class ReviewAnswerAdapter: ListAdapter<QuestionWithAnswer, ReviewAnswerAdapter.QuestionViewHolder>(QuestionWithAnswerDiffCallback()) {

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val explanationTextView: TextView = itemView.findViewById(R.id.explanation_id)
        val questionTextView: TextView = itemView.findViewById(R.id.question_text)
        val optionA: RadioButton = itemView.findViewById(R.id.option_a)
        val optionB: RadioButton = itemView.findViewById(R.id.option_b)
        val optionC: RadioButton = itemView.findViewById(R.id.option_c)
        val optionD: RadioButton = itemView.findViewById(R.id.option_d)
    }

    class QuestionWithAnswerDiffCallback : DiffUtil.ItemCallback<QuestionWithAnswer>() {
        override fun areItemsTheSame(oldItem: QuestionWithAnswer, newItem: QuestionWithAnswer): Boolean {
            return oldItem.question.id == newItem.question.id
        }

        override fun areContentsTheSame(oldItem: QuestionWithAnswer, newItem: QuestionWithAnswer): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_answers_rv_layout, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = getItem(position)
        val question = item.question
        val userAnswer = item.userAnswer

        holder.questionTextView.text = question.question

        val answers = question.answers
        holder.optionA.text = answers.answerA ?: ""
        holder.optionB.text = answers.answerB ?: ""
        holder.optionC.text = answers.answerC ?: ""
        holder.optionD.text = answers.answerD ?: ""

        handleAnswerVisibilityAndClickability(holder, answers)

        holder.explanationTextView.text = question.explanation ?: "No explanation available"

        val correctAnswers = question.correctAnswers

        markAnswers(holder, correctAnswers, userAnswer?.selectedAnswer)
    }

    private fun markAnswers(
        holder: QuestionViewHolder,
        correctAnswers: CorrectAnswers,
        selectedAnswer: String?
    ) {
        resetRadioButtonBackgrounds(holder)

        val context = holder.itemView.context
        val correctColor = context.getColor(R.color.green)
        val incorrectColor = context.getColor(R.color.red)

        markAnswer(holder.optionA, correctAnswers.answerAValid, selectedAnswer, correctColor, incorrectColor)
        markAnswer(holder.optionB, correctAnswers.answerBValid, selectedAnswer, correctColor, incorrectColor)
        markAnswer(holder.optionC, correctAnswers.answerCValid, selectedAnswer, correctColor, incorrectColor)
        markAnswer(holder.optionD, correctAnswers.answerDValid, selectedAnswer, correctColor, incorrectColor)
    }

    private fun markAnswer(
        radioButton: RadioButton,
        isCorrect: String,
        selectedAnswer: String?,
        correctColor: Int,
        incorrectColor: Int
    ) {
        if (radioButton.text.toString() == selectedAnswer) {
            radioButton.setBackgroundColor(if (isCorrect == "true") correctColor else incorrectColor)
        } else if (isCorrect == "true") {
            radioButton.setBackgroundColor(correctColor)
        } else {
            radioButton.setBackgroundColor(radioButton.context.getColor(R.color.purple))
        }
    }

    private fun resetRadioButtonBackgrounds(holder: QuestionViewHolder) {
        val defaultColor = holder.itemView.context.getColor(R.color.purple)
        holder.optionA.setBackgroundColor(defaultColor)
        holder.optionB.setBackgroundColor(defaultColor)
        holder.optionC.setBackgroundColor(defaultColor)
        holder.optionD.setBackgroundColor(defaultColor)
    }

    private fun handleAnswerVisibilityAndClickability(holder: QuestionViewHolder, answers: Answers) {
        handleRadioButtonVisibility(holder.optionA, answers.answerA)
        handleRadioButtonVisibility(holder.optionB, answers.answerB)
        handleRadioButtonVisibility(holder.optionC, answers.answerC)
        handleRadioButtonVisibility(holder.optionD, answers.answerD)
    }

    private fun handleRadioButtonVisibility(radioButton: RadioButton, answer: String?) {
        if (answer.isNullOrEmpty()) {
            radioButton.visibility = View.GONE
            radioButton.isEnabled = false
        } else {
            radioButton.visibility = View.VISIBLE
            radioButton.isEnabled = false
        }
    }
}
