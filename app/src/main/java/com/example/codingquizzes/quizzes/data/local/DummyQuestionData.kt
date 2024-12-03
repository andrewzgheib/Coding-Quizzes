package com.example.codingquizzes.quizzes.data.local

import com.example.codingquizzes.quizzes.data.model.Question

object DummyQuestionData {
    fun generateQuestions(quizId: Int, level: String): List<Question> {
        return when (quizId) {
            1 -> generateCSharpQuestions(quizId, level)
            else -> emptyList()
        }
    }

    private fun generateCSharpQuestions(quizId: Int, level: String): List<Question> {
        val beginnerQuestions = listOf(
            Question(
                id = 1,
                quizId = quizId,
                type = "multiple_choice",
                level = "beginner",
                questionText = "What is the purpose of the 'var' keyword in C#?",
                questionType = "single_select",
                firstOption = "To declare a variable with implicit typing",
                secondOption = "To create a new instance of an object",
                thirdOption ="test",
                correctAnswer = "To declare a variable with implicit typing"
            ),
            Question(
                id = 2,
                quizId = quizId,
                type = "multiple_choice",
                level = "beginner",
                questionText = "Which data type is used to store whole numbers in C#?",
                questionType = "single_select",
                firstOption = "float",
                secondOption = "int",
                thirdOption ="test",
                correctAnswer = "int"
            ),
            Question(
                id = 3,
                quizId = quizId,
                type = "multiple_choice",
                level = "beginner",
                questionText = "Which data type is used to store whole numbers in C#?",
                questionType = "single_select",
                firstOption = "float",
                secondOption = "int",
                thirdOption ="test",
                correctAnswer = "int"
            ),
            Question(
                id = 4,
                quizId = quizId,
                type = "multiple_choice",
                level = "beginner",
                questionText = "Which data type is used to store whole numbers in C#?",
                questionType = "single_select",
                firstOption = "float",
                secondOption = "int",
                thirdOption ="test",
                correctAnswer = "int"
            ),
            Question(
                id = 5,
                quizId = quizId,
                type = "multiple_choice",
                level = "beginner",
                questionText = "Which data type is used to store whole numbers in C#?",
                questionType = "single_select",
                firstOption = "float",
                secondOption = "int",
                thirdOption ="test",
                correctAnswer = "int"
            )
        )

        val intermediateQuestions = listOf(
            Question(
                id = 3,
                quizId = quizId,
                type = "multiple_choice",
                level = "intermediate",
                questionText = "What is the purpose of the 'using' statement in C#?",
                questionType = "single_select",
                firstOption = "To import namespaces",
                secondOption = "To define a method",
                thirdOption ="test",
                correctAnswer = "To import namespaces"
            ),
            Question(
                id = 4,
                quizId = quizId,
                type = "multiple_choice",
                level = "intermediate",
                questionText = "What is the default value of a boolean variable in C#?",
                questionType = "single_select",
                firstOption = "True",
                secondOption = "False",
                thirdOption ="test",
                correctAnswer = "False"
            )
        )

        return when (level.lowercase()) {
            "beginner" -> beginnerQuestions
            "intermediate" -> intermediateQuestions
            else -> beginnerQuestions + intermediateQuestions
        }
    }
}
