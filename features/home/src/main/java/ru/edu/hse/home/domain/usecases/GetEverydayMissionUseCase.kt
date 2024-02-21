package ru.edu.hse.home.domain.usecases

import ru.edu.hse.home.domain.repositories.EverydayMissionRepository
import javax.inject.Inject

class GetEverydayMissionUseCase @Inject constructor(
    private val everydayMissionRepository: EverydayMissionRepository
) {

    suspend fun getEveryDayMission() : String {
        return everydayMissionRepository.getEverydayMission()
    }

}