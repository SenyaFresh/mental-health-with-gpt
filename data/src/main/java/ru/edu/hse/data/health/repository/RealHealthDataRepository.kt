package ru.edu.hse.data.health.repository

import androidx.activity.result.contract.ActivityResultContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.common.flow.LazyFlowLoaderFactory
import ru.edu.hse.data.health.entities.HealthDataEntity
import ru.edu.hse.data.health.sources.HealthDataSource
import javax.inject.Inject

class RealHealthDataRepository @Inject constructor(
    private val healthDataSource: HealthDataSource,
    scope: CoroutineScope,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : HealthDataRepository {

    private val healthDataLazyFlowLoader = lazyFlowLoaderFactory.create {
        healthDataSource.getHealthData()
    }

    override val permissions: Set<String>
        get() = healthDataSource.permissions

    init {
        scope.launch {
            while (isActive) {
                healthDataLazyFlowLoader.newAsyncLoad()
                delay(10000)
            }
        }
    }

    override fun checkInstalled(): Boolean = healthDataSource.checkInstalled()

    override suspend fun hasAllPermissions(): Boolean = healthDataSource.hasAllPermissions()

    override fun requestPermissionActivityContract(): ActivityResultContract<Set<String>, Set<String>> =
        healthDataSource.requestPermissionActivityContract()

    override suspend fun getHealthData(): Flow<ResultContainer<HealthDataEntity>> {
        return healthDataLazyFlowLoader.listen()
    }

    override suspend fun getEverydayMission(): String {
        // TODO
        return ""
    }
}