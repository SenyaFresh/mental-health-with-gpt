package ru.edu.hse.data.health.sources

import androidx.activity.result.contract.ActivityResultContract
import ru.edu.hse.data.health.entities.HealthDataEntity

interface HealthDataSource {

    /**
     * Get set of permissions, that app needs.
     */
    val permissions: Set<String>

    /**
     * Check if Health Connect installed.
     */
    fun checkInstalled() : Boolean

    /**
     * Check if app has all permissions.
     */
    suspend fun hasAllPermissions(): Boolean

    /**
     * Get activity result contract to request permissions.
     */
    fun requestPermissionActivityContract(): ActivityResultContract<Set<String>, Set<String>>

    /**
     * Get [HealthDataEntity] from database.
     */
    suspend fun getHealthData() : HealthDataEntity

}