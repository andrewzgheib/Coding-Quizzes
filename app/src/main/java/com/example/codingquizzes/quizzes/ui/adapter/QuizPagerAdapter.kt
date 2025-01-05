package com.example.codingquizzes.quizzes.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.codingquizzes.quizzes.data.model.Question
import com.example.codingquizzes.quizzes.ui.view.fragments.MCQFragment

class QuizPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val questions: List<Question>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = questions.size

    override fun createFragment(position: Int): Fragment {
        return MCQFragment.newInstance(questions[position])
    }
}

