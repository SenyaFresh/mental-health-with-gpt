package ru.edu.hse.data.mental.test.entities

data class MentalTestQuestionEntity(
    val id: String,
    val answers: List<String>?,
    val question: String,
    val questionType: String
)