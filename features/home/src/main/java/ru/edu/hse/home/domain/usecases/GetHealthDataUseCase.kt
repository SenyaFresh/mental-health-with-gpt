package ru.edu.hse.home.domain.usecases

import ru.edu.hse.home.domain.entities.HealthData
import ru.edu.hse.home.domain.exceptions.HealthConnectNotInstalledException
import ru.edu.hse.home.domain.exceptions.PermissionsNotGrantedException
import ru.edu.hse.home.domain.repositories.HealthRepository
import javax.inject.Inject

class GetHealthDataUseCase @Inject constructor(
    private val healthRepository: HealthRepository
) {

    suspend fun getHealthData() : HealthData {

        if (!healthRepository.checkInstalled()) {
            throw HealthConnectNotInstalledException()
        } else if (!healthRepository.hasAllPermissions()) {
            throw PermissionsNotGrantedException()
        } else {
            return healthRepository.getHealthData()
        }

    }

}