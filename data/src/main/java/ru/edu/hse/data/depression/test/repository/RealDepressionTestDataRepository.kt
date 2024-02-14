package ru.edu.hse.data.depression.test.repository

import ru.edu.hse.data.depression.test.entities.DepressionTestEntity
import ru.edu.hse.data.depression.test.sources.DepressionTestDataSource
import javax.inject.Inject

class RealDepressionTestDataRepository @Inject constructor(
    private val depressionTestDataSource: DepressionTestDataSource
) : DepressionTestDataRepository {

    override suspend fun getDepressionTest(): DepressionTestEntity {
        return depressionTestDataSource.getDepressionTest()
    }

}