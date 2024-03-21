package ru.edu.hse.home.domain.usecases

import ru.edu.hse.home.domain.entities.MentalTestQuestionEntity
import ru.edu.hse.home.domain.repositories.MentalTestRepository
import javax.inject.Inject

class SetMentalTestAnswerUseCase @Inject constructor(
    private val mentalTestRepository: MentalTestRepository
) {

    suspend fun setMentalTestAnswer(question: MentalTestQuestionEntity, answer: String) {
        mentalTestRepository.setMentalTestAnswer(question, answer)
    }

}