    package com.example.codingquizzes.quizzes.data.model

    import androidx.room.ColumnInfo
    import androidx.room.Entity
    import androidx.room.PrimaryKey

    @Entity(tableName = "quiz_list_table")
    data class Quiz(
        @PrimaryKey(autoGenerate = true) var id: Int,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "prerequisite") val prerequisite: String
    )