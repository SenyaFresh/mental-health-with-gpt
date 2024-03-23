package ru.edu.hse.mentalhealthwithgpt.glue.home.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.data.mental.test.repository.MentalTestDataRepository
import ru.edu.hse.home.domain.entities.MentalTestEntity
import ru.edu.hse.home.domain.entities.MentalTestQuestionEntity
import ru.edu.hse.home.domain.repositories.MentalTestRepository
import ru.edu.hse.mentalhealthwithgpt.glue.home.mappers.toMentalTestEntity
import ru.edu.hse.mentalhealthwithgpt.glue.home.mappers.toMentalTestQuestionDataEntity
import javax.inject.Inject

class AdapterMentalTestRepository @Inject constructor(
    private val mentalTestDataRepository: MentalTestDataRepository
) : MentalTestRepository {

    override fun getMentalTest(): Flow<ResultContainer<MentalTestEntity>> {
        return mentalTestDataRepository.getMentalTest()
            .map { container -> container.map { it.toMentalTestEntity() } }
    }

    override suspend fun setMentalTestAnswer(
        mentalQuestion: MentalTestQuestionEntity,
        answer: String
    ) {
        mentalTestDataRepository.setMentalTestAnswer(
            mentalQuestion.toMentalTestQuestionDataEntity(),
            answer
        )
    }
}