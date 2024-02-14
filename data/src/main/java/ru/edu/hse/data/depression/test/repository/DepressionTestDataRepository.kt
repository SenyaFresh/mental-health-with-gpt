package ru.edu.hse.data.depression.test.repository

import ru.edu.hse.data.depression.test.entities.DepressionTestEntity

interface DepressionTestDataRepository {

    /**
     * Get depression test.
     */
    suspend fun getDepressionTest() : DepressionTestEntity

}