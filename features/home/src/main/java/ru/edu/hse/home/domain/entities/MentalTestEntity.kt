package ru.edu.hse.home.domain.entities

data class MentalTestEntity(
    val id: String,
    val mentalTestQuestions: List<MentalTestQuestionEntity>,
    val name: String
)