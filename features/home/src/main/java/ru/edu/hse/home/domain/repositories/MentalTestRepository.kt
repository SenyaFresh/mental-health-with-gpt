package ru.edu.hse.home.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.MentalTestEntity
import ru.edu.hse.home.domain.entities.MentalTestQuestionEntity

interface MentalTestRepository {

    /**
     * Get mental test.
     */
    fun getMentalTest() : Flow<ResultContainer<MentalTestEntity>>

    /**
     * Set mental test answer.
     */
    suspend fun setMentalTestAnswer(mentalQuestion: MentalTestQuestionEntity, answer: String)

}