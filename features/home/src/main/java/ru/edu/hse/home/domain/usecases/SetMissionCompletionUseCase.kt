package ru.edu.hse.home.domain.usecases

import ru.edu.hse.home.domain.entities.EverydayMissionEntity
import ru.edu.hse.home.domain.repositories.EverydayMissionsRepository
import javax.inject.Inject

class SetMissionCompletionUseCase @Inject constructor(
    private val everydayMissionsRepository: EverydayMissionsRepository
) {

    /**
     * Change completion status of mission.
     */
    suspend fun setEverydayMissionsCompletion(mission: EverydayMissionEntity) {
        everydayMissionsRepository.setEverydayMissionsCompletion(mission)
    }

}