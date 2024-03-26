package ru.edu.hse.data.health.repository

import androidx.activity.result.contract.ActivityResultContract
import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.data.health.entities.EverydayMissionDataEntity
import ru.edu.hse.data.health.entities.EverydayMissionsListDataEntity
import ru.edu.hse.data.health.entities.HealthDataEntity

interface HealthDataRepository {

    /**
     * Get set of permissions, that app needs.
     */
    val permissions: Set<String>

    /**
     * Get activity result contract to request permissions.
     */
    fun requestPermissionActivityContract(): ActivityResultContract<Set<String>, Set<String>>

    /**
     * Get up-to-date [HealthDataEntity] from repository.
     */
    suspend fun getHealthData() : Flow<ResultContainer<HealthDataEntity>>

    /**
     * Get everyday mission from repository.
     */
    suspend fun getEverydayMissions() : Flow<ResultContainer<EverydayMissionsListDataEntity>>

    /**
     * Set completion to [mission].
     */
    suspend fun setMissionCompletion(mission: EverydayMissionDataEntity)

    /**
     * Reload health data.
     */
    fun reloadHealthData()

}