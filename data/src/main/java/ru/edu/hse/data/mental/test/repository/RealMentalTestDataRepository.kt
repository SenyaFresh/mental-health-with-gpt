package ru.edu.hse.data.mental.test.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.common.flow.LazyFlowLoaderFactory
import ru.edu.hse.data.RemoteConfigManager
import ru.edu.hse.data.mental.test.entities.MentalTestDataEntity
import ru.edu.hse.data.mental.test.entities.MentalTestQuestionEntity
import ru.edu.hse.data.mental.test.exceptions.MentalTestRepositoryException
import ru.edu.hse.data.mental.test.source.MentalTestDataSource
import javax.inject.Inject

class RealMentalTestDataRepository @Inject constructor(
    private val source: MentalTestDataSource,
    scope: CoroutineScope,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : MentalTestDataRepository {

    private val mentalTestLazyFlowLoader = lazyFlowLoaderFactory.create {
        source.getMentalTest()
    }

    init {
        scope.launch {
            RemoteConfigManager.remoteConfigUpdates.collect {
                if (it == null) {
                    mentalTestLazyFlowLoader.update(
                        ResultContainer.Error(MentalTestRepositoryException())
                    )
                } else {
                    mentalTestLazyFlowLoader.newAsyncLoad(silently = true)
                }
            }
        }
    }

    override fun getMentalTest(): Flow<ResultContainer<MentalTestDataEntity>> {
        return mentalTestLazyFlowLoader.listen()
    }

    override suspend fun setMentalTestAnswer(mentalQuestion: MentalTestQuestionEntity, answer: String) {
        source.setMentalTestAnswer(mentalQuestion, answer)
    }

    companion object {
        const val USERS_COLLECTION = "users"
        const val ANSWERS_COLLECTION = "answers"
    }

}