package com.example.codingquizzes.quizzes.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.codingquizzes.quizzes.data.model.UserAnswer

@Dao
interface UserAnswerDao {

    @Query("SELECT * FROM user_answers")
    fun getUserAnswers(): LiveData<List<UserAnswer>>

    @Insert
    suspend fun insert(userAnswer: UserAnswer)

    @Query("SELECT * FROM user_answers WHERE question_id = :questionId")
    suspend fun getAnswerForQuestion(questionId: Int): UserAnswer

    @Update
    suspend fun update(userAnswer: UserAnswer)
}

