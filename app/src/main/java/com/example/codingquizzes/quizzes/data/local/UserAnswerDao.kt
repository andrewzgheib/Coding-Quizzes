package com.example.codingquizzes.quizzes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.codingquizzes.quizzes.data.model.UserAnswer
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAnswer(userAnswer: UserAnswer)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserAnswer(userAnswer: UserAnswer)

    @Query("SELECT * FROM user_answer WHERE question_id = :questionId LIMIT 1")
    suspend fun getUserAnswerForQuestion(questionId: Int): UserAnswer?

    @Query("SELECT * FROM user_answer")
    fun getAllUserAnswers(): Flow<List<UserAnswer>>

    @Query("DELETE FROM user_answer")
    suspend fun deleteAllUserAnswers()
}
