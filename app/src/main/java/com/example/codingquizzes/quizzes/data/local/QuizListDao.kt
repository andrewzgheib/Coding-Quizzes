package com.example.codingquizzes.quizzes.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codingquizzes.quizzes.data.model.Quiz

@Dao
interface QuizListDao {
    @Query("SELECT * FROM quizzes")
    fun getQuizzes(): LiveData<List<Quiz>>
    /*
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(quiz: Quiz)*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quizlist: List<Quiz>)

    /*    @Query("UPDATE quizzes SET quiz_title= :title, quiz_description= :description, quiz_prerequisite= :prerequisite WHERE id= :id")
        suspend fun update(id: Int, title: String, description: String, prerequisite: String)

        @Delete
        suspend fun delete(quiz: Quiz)*/
}
