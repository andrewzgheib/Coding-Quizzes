package com.example.codingquizzes.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.codingquizzes.R
import com.example.codingquizzes.data.model.Question
import com.example.codingquizzes.data.model.UserAnswer
import com.example.codingquizzes.ui.vm.UserAnswerViewModel

class MCQFragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var optionA: RadioButton
    private lateinit var optionB: RadioButton
    private lateinit var optionC: RadioButton
    private lateinit var optionD: RadioButton
    private lateinit var optionsGroup: RadioGroup

    private val userAnswerViewModel: UserAnswerViewModel by activityViewModels()

    private var question: Question? = null

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
        optionsGroup = view.findViewById(R.id.options_radio_group)

        question = arguments?.getParcelable("question", Question::class.java)
        question?.let {
            questionText.text = it.question

            setAnswer(optionA, it.answers.answerA)
            setAnswer(optionB, it.answers.answerB)
            setAnswer(optionC, it.answers.answerC)
            setAnswer(optionD, it.answers.answerD)
        }

        optionsGroup.setOnCheckedChangeListener { _, checkedId ->
            handleOptionSelection(checkedId)
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

    private fun handleOptionSelection(checkedId: Int) {
        val selectedOption = when (checkedId) {
            R.id.option_a -> optionA.text.toString()
            R.id.option_b -> optionB.text.toString()
            R.id.option_c -> optionC.text.toString()
            R.id.option_d -> optionD.text.toString()
            else -> null
        }

        question?.let { question ->
            if (selectedOption != null) {
                val userAnswer = UserAnswer(
                    questionId = question.id,
                    selectedAnswer = selectedOption
                )
                userAnswerViewModel.insertOrUpdateUserAnswer(userAnswer)
            } else {
                val userAnswer = UserAnswer(
                    questionId = question.id,
                    selectedAnswer = null
                )
                userAnswerViewModel.insertOrUpdateUserAnswer(userAnswer)
            }
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
