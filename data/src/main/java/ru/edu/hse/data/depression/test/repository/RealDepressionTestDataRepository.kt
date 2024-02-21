package ru.edu.hse.data.depression.test.repository

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.common.flow.LazyFlowLoaderFactory
import ru.edu.hse.data.depression.test.entities.DepressionTestEntity
import ru.edu.hse.data.depression.test.sources.DepressionTestDataSource
import javax.inject.Inject

class RealDepressionTestDataRepository @Inject constructor(
    private val depressionTestDataSource: DepressionTestDataSource,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : DepressionTestDataRepository {

    private val depressionTestLazyFlowLoader = lazyFlowLoaderFactory.create {
        depressionTestDataSource.getDepressionTest()
    }

    override fun getDepressionTest(): Flow<ResultContainer<DepressionTestEntity>> {
        return depressionTestLazyFlowLoader.listen()
    }

}