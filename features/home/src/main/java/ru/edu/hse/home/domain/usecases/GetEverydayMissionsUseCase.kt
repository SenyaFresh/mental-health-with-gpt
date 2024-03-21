package ru.edu.hse.home.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.EverydayMissionsListEntity
import ru.edu.hse.home.domain.repositories.EverydayMissionsRepository
import javax.inject.Inject

class GetEverydayMissionsUseCase @Inject constructor(
    private val everydayMissionsRepository: EverydayMissionsRepository
) {

    suspend fun getEverydayMissions() : Flow<ResultContainer<EverydayMissionsListEntity>> {
        return everydayMissionsRepository.getEverydayMissions()
    }

}