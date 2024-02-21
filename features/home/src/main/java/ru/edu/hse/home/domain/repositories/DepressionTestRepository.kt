package ru.edu.hse.home.domain.repositories

import ru.edu.hse.home.domain.entities.DepressionTest

interface DepressionTestRepository {

    /**
     * Get depression test.
     */
    suspend fun getDepressionTest() : DepressionTest

    /**
     * Get user's depression points. Returns -1 if user did not pass the test.
     */
    suspend fun getUserDepressionPoints() : Int

    /**
     * Update user's depression points.
     */
    suspend fun updateUserDepressionPoints(points: Int)

}