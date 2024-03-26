package ru.edu.hse.data.health.sources

import androidx.activity.result.contract.ActivityResultContract
import ru.edu.hse.data.health.entities.EverydayMissionDataEntity
import ru.edu.hse.data.health.entities.EverydayMissionsListDataEntity
import ru.edu.hse.data.health.entities.HealthDataEntity

interface HealthDataSource {

    /**
     * Get set of permissions, that app needs.
     */
    val permissions: Set<String>

    /**
     * Get activity result contract to request permissions.
     */
    fun requestPermissionActivityContract(): ActivityResultContract<Set<String>, Set<String>>

    /**
     * Get [HealthDataEntity] from database.
     */
    suspend fun getHealthData() : HealthDataEntity


    /**
     * Get list of everyday missions from database.
     */
    suspend fun getEverydayMissions() : EverydayMissionsListDataEntity

    /**
     * Set mission completed flag to true in database.
     */
    suspend fun setMissionCompletion(mission: EverydayMissionDataEntity)

}