package com.example.codingquizzes.quizzes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


import com.google.gson.annotations.SerializedName

@Entity(tableName = "quizzes")
data class Quiz(
    @PrimaryKey(autoGenerate = true) var id: Int?,

    @ColumnInfo(name = "category")
    @SerializedName("name")
    val category: String?,

    val iconResId: Int? = null,
    var categoryGroup: String? = null
)
