package com.example.codingquizzes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    @SerializedName("id")
    val id: Int,

    @SerializedName("question")
    val question: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("answers")
    val answers: Answers,

    @SerializedName("multiple_correct_answers")
    val multipleCorrectAnswers: String,

    @SerializedName("correct_answers")
    val correctAnswers: CorrectAnswers,

    @SerializedName("explanation")
    val explanation: String?,

    @SerializedName("tip")
    val tip: String?,

    @SerializedName("tags")
    val tags: List<Tag>,

    @SerializedName("category")
    val category: String,

    @SerializedName("difficulty")
    val difficulty: String
) : Parcelable

@Parcelize
data class Answers(
    @SerializedName("answer_a")
    val answerA: String?,

    @SerializedName("answer_b")
    val answerB: String?,

    @SerializedName("answer_c")
    val answerC: String?,

    @SerializedName("answer_d")
    val answerD: String?,

    @SerializedName("answer_e")
    val answerE: String?,

    @SerializedName("answer_f")
    val answerF: String?
) : Parcelable

@Parcelize
data class CorrectAnswers(
    @SerializedName("answer_a_correct")
    val answerAValid: String,

    @SerializedName("answer_b_correct")
    val answerBValid: String,

    @SerializedName("answer_c_correct")
    val answerCValid: String,

    @SerializedName("answer_d_correct")
    val answerDValid: String,

    @SerializedName("answer_e_correct")
    val answerEValid: String,

    @SerializedName("answer_f_correct")
    val answerFValid: String
) : Parcelable

@Parcelize
data class Tag(
    @SerializedName("name")
    val name: String
) : Parcelable
