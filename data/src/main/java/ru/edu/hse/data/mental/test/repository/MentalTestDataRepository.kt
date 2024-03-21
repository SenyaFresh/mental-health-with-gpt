package ru.edu.hse.data.mental.test.repository

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.data.mental.test.entities.MentalTestDataEntity

interface MentalTestDataRepository {

    /**
     * Get depression test.
     */
    fun getDepressionTest() : Flow<ResultContainer<MentalTestDataEntity>>

}