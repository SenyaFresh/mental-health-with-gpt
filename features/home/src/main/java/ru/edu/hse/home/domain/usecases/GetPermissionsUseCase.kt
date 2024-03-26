package ru.edu.hse.home.domain.usecases

import androidx.activity.result.contract.ActivityResultContract
import ru.edu.hse.home.domain.repositories.HealthRepository
import javax.inject.Inject

class GetPermissionsUseCase @Inject constructor(
    private val healthRepository: HealthRepository
) {

    /**
     * Get needed permissions.
     */
    fun getSetOfPermissions(): Set<String> = healthRepository.permissions

    /**
     * Get Activity ResultContract.
     */
    fun requestPermissionActivityContract(): ActivityResultContract<Set<String>, Set<String>> =
        healthRepository.requestPermissionActivityContract()

}