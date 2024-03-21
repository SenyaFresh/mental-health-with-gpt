package ru.edu.hse.home.domain.entities

data class MentalTestQuestionEntity(
    val id: String,
    val answers: List<String>?,
    val question: String,
    val questionType: String
)