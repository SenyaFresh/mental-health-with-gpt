package ru.edu.hse.data.mental.test.entities

data class MentalTestDataEntity(
    val id: String,
    val mentalTestQuestions: List<MentalTestQuestionEntity>,
    val name: String
)