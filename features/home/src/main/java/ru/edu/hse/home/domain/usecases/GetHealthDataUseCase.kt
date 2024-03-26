package ru.edu.hse.home.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.HealthData
import ru.edu.hse.home.domain.repositories.HealthRepository
import javax.inject.Inject

class GetHealthDataUseCase @Inject constructor(
    private val healthRepository: HealthRepository
) {

    /**
     * Get user's health data.
     */
    suspend fun getHealthData(): Flow<ResultContainer<HealthData>> {
        return healthRepository.getHealthData()
    }

    /**
     * Reload user's health data.
     */
    fun reloadHealthData() = healthRepository.reloadHealthData()

}