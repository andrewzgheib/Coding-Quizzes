package com.example.codingquizzes.quizzes.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codingquizzes.quizzes.data.model.Question

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions WHERE quiz_id = :quizId AND level = :level")
    fun getQuestionsByQuizAndLevel(quizId: Int, level: String): LiveData<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questionList: List<Question>)

}
