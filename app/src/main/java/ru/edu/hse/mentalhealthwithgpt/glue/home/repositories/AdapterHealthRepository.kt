package ru.edu.hse.mentalhealthwithgpt.glue.home.repositories

import androidx.activity.result.contract.ActivityResultContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.data.health.repository.HealthDataRepository
import ru.edu.hse.home.domain.entities.HealthData
import ru.edu.hse.home.domain.repositories.HealthRepository
import ru.edu.hse.mentalhealthwithgpt.glue.home.mappers.toHealthData
import javax.inject.Inject

class AdapterHealthRepository @Inject constructor(
    private val healthDataRepository: HealthDataRepository
) : HealthRepository {

    override val permissions: Set<String>
        get() = healthDataRepository.permissions

    override fun checkInstalled(): Boolean {
        return healthDataRepository.checkInstalled()
    }

    override suspend fun hasAllPermissions(): Boolean {
        return healthDataRepository.hasAllPermissions()
    }

    override fun requestPermissionActivityContract(): ActivityResultContract<Set<String>, Set<String>> {
        return healthDataRepository.requestPermissionActivityContract()
    }

    override suspend fun getHealthData(): Flow<ResultContainer<HealthData>> {
        return healthDataRepository.getHealthData()
            .map { container -> container.map { it.toHealthData() } }
    }

}