package com.example.codingquizzes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codingquizzes.data.model.UserPref

@Dao
interface UserPrefDAO {
    @Query("SELECT * FROM prefs WHERE id = 1")
    fun getPreference(): UserPref?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPreference(preference: UserPref)

    @Query("UPDATE prefs SET bgColor = :color WHERE id = 1")
    fun updateBackgroundColor(color: String)
}