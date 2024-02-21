package ru.edu.hse.data.depression.test.repository

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.data.depression.test.entities.DepressionTestEntity

interface DepressionTestDataRepository {

    /**
     * Get depression test.
     */
    fun getDepressionTest() : Flow<ResultContainer<DepressionTestEntity>>

}