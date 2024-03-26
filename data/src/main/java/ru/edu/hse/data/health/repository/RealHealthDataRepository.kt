package ru.edu.hse.data.health.repository

import androidx.activity.result.contract.ActivityResultContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.common.flow.LazyFlowLoaderFactory
import ru.edu.hse.data.health.entities.EverydayMissionDataEntity
import ru.edu.hse.data.health.entities.EverydayMissionsListDataEntity
import ru.edu.hse.data.health.entities.HealthDataEntity
import ru.edu.hse.data.health.sources.HealthDataSource
import javax.inject.Inject

class RealHealthDataRepository @Inject constructor(
    private val healthDataSource: HealthDataSource,
    scope: CoroutineScope,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : HealthDataRepository {

    private var subscribed = false

    private val healthDataLazyFlowLoader = lazyFlowLoaderFactory.create {
        healthDataSource.getHealthData()
    }

    private val everydayMissionsLazyFlowLoader = lazyFlowLoaderFactory.create {
        healthDataSource.getEverydayMissions()
    }

    override val permissions: Set<String>
        get() = healthDataSource.permissions

    init {
        scope.launch {
            while (isActive) {
                if (subscribed) {
                    healthDataLazyFlowLoader.newAsyncLoad(silently = true)
                }
                delay(60000)
            }
        }
    }

    override fun requestPermissionActivityContract(): ActivityResultContract<Set<String>, Set<String>> =
        healthDataSource.requestPermissionActivityContract()

    override suspend fun getHealthData(): Flow<ResultContainer<HealthDataEntity>> {
        subscribed = true
        return healthDataLazyFlowLoader.listen()
    }

    override suspend fun getEverydayMissions(): Flow<ResultContainer<EverydayMissionsListDataEntity>> {
        return everydayMissionsLazyFlowLoader.listen()
    }

    override suspend fun setMissionCompletion(mission: EverydayMissionDataEntity) {
        healthDataSource.setMissionCompletion(mission)
    }

    override fun reloadHealthData() {
        healthDataLazyFlowLoader.newAsyncLoad()
    }

}