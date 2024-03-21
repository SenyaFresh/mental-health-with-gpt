package ru.edu.hse.data.mental.test.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.common.flow.LazyFlowLoaderFactory
import ru.edu.hse.data.RemoteConfigManager
import ru.edu.hse.data.mental.test.entities.MentalTestDataEntity
import ru.edu.hse.data.mental.test.exceptions.MentalTestRepositoryException
import javax.inject.Inject

class RealMentalTestDataRepository @Inject constructor(
    scope: CoroutineScope,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : MentalTestDataRepository {

    private val depressionTestLazyFlowLoader = lazyFlowLoaderFactory.create {
        RemoteConfigManager.getMentalTest()
    }

    init {
        scope.launch {
            RemoteConfigManager.remoteConfigUpdates.collect {
                if (it == null) {
                    depressionTestLazyFlowLoader.update(
                        ResultContainer.Error(
                            MentalTestRepositoryException()
                        )
                    )
                } else {
                    depressionTestLazyFlowLoader.newAsyncLoad(silently = true)
                }
            }
        }
    }

    override fun getDepressionTest(): Flow<ResultContainer<MentalTestDataEntity>> {
        return depressionTestLazyFlowLoader.listen()
    }

}