package ru.edu.hse.home.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.DepressionTest

interface DepressionTestRepository {

    /**
     * Get depression test.
     */
    fun getDepressionTest() : Flow<ResultContainer<DepressionTest>>

    /**
     * Get user's depression points. Returns -1 if user did not pass the test.
     */
    fun getUserDepressionPoints() : Flow<ResultContainer<Int>>

    /**
     * Update user's depression points.
     */
    suspend fun updateUserDepressionPoints(points: Int)

}