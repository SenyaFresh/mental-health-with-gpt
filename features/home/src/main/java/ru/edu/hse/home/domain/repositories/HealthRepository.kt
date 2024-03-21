package ru.edu.hse.home.domain.repositories

import androidx.activity.result.contract.ActivityResultContract
import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.HealthData

interface HealthRepository {
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
     * Get up-to-date [HealthData] from repository.
     */
    suspend fun getHealthData() : Flow<ResultContainer<HealthData>>

}