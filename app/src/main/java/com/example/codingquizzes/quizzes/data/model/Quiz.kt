    package com.example.codingquizzes.quizzes.data.model

    import androidx.room.ColumnInfo
    import androidx.room.Entity
    import androidx.room.PrimaryKey

    @Entity(tableName = "quizzes")
    data class Quiz(
        @PrimaryKey(autoGenerate = true) var id: Int,
        @ColumnInfo(name = "quiz_title") val title: String,
        @ColumnInfo(name = "quiz_description") val description: String,
        @ColumnInfo(name = "quiz_prerequisite") val prerequisite: String
    )