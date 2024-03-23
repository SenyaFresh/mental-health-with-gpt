package ru.edu.hse.mentalhealthwithgpt.glue.home.mappers

import ru.edu.hse.data.mental.test.entities.MentalTestDataEntity
import ru.edu.hse.data.mental.test.entities.MentalTestQuestionDataEntity
import ru.edu.hse.home.domain.entities.MentalTestEntity
import ru.edu.hse.home.domain.entities.MentalTestQuestionEntity

fun MentalTestQuestionDataEntity.toMentalTestQuestionEntity(): MentalTestQuestionEntity {
    return MentalTestQuestionEntity(id, answers, question, questionType)
}

fun MentalTestQuestionEntity.toMentalTestQuestionDataEntity(): MentalTestQuestionDataEntity {
    return MentalTestQuestionDataEntity(id, answers, question, questionType)
}

fun MentalTestDataEntity.toMentalTestEntity(): MentalTestEntity {
    return MentalTestEntity(
        id,
        mentalTestQuestions.map { it.toMentalTestQuestionEntity() },
        name
    )
}
