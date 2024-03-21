package ru.edu.hse.data.mental.test.entities

data class MentalTestDataEntity(
    val id: String,
    val mentalTestQuestions: List<MentalTestQuestionDataEntity>,
    val name: String
)