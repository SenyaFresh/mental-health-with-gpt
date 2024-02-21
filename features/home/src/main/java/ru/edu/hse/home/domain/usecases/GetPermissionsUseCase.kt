package ru.edu.hse.home.domain.usecases

import androidx.activity.result.contract.ActivityResultContract
import ru.edu.hse.home.domain.repositories.HealthRepository
import javax.inject.Inject

class GetPermissionsUseCase @Inject constructor(
    private val healthRepository: HealthRepository
) {

    fun getSetOfPermissions(): Set<String> = healthRepository.permissions

    fun requestPermissionActivityContract(): ActivityResultContract<Set<String>, Set<String>> =
        healthRepository.requestPermissionActivityContract()

}