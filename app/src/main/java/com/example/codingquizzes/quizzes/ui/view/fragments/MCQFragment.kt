package com.example.codingquizzes.quizzes.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.codingquizzes.R
import com.example.codingquizzes.quizzes.data.model.Question

class MCQFragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionA: RadioButton
    private lateinit var optionB: RadioButton
    private lateinit var optionC: RadioButton
    private lateinit var optionD: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mcq, container, false)

        questionText = view.findViewById(R.id.question_text)
        optionA = view.findViewById(R.id.option_a)
        optionB = view.findViewById(R.id.option_b)
        optionC = view.findViewById(R.id.option_c)
        optionD = view.findViewById(R.id.option_d)

        val question = arguments?.getParcelable("question", Question::class.java)
        question?.let {
            questionText.text = it.question

            setAnswer(optionA, it.answers.answerA)
            setAnswer(optionB, it.answers.answerB)
            setAnswer(optionC, it.answers.answerC)
            setAnswer(optionD, it.answers.answerD)
        }

        return view
    }

    private fun setAnswer(radioButton: RadioButton, answer: String?) {
        if (answer.isNullOrEmpty() || answer == "NaN") {
            radioButton.visibility = View.GONE
        } else {
            radioButton.visibility = View.VISIBLE
            radioButton.text = answer
        }
    }

    companion object {
        fun newInstance(question: Question): MCQFragment {
            val fragment = MCQFragment()
            val args = Bundle()
            args.putParcelable("question", question)
            fragment.arguments = args
            return fragment
        }
    }
}
