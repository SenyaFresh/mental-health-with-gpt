package ru.edu.hse.data.depression.test.sources

import ru.edu.hse.data.depression.test.entities.DepressionTestEntity

interface DepressionTestDataSource {

    /**
     * Get depression test.
     */
    suspend fun getDepressionTest() : DepressionTestEntity

}