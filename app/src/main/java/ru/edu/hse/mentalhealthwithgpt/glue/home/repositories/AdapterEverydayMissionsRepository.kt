package ru.edu.hse.mentalhealthwithgpt.glue.home.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.data.health.repository.HealthDataRepository
import ru.edu.hse.home.domain.entities.EverydayMissionEntity
import ru.edu.hse.home.domain.entities.EverydayMissionsListEntity
import ru.edu.hse.home.domain.repositories.EverydayMissionsRepository
import ru.edu.hse.mentalhealthwithgpt.glue.home.mappers.toEverydayMissionDataEntity
import ru.edu.hse.mentalhealthwithgpt.glue.home.mappers.toEverydayMissionListEntity
import javax.inject.Inject

class AdapterEverydayMissionsRepository @Inject constructor(
    private val healthDataRepository: HealthDataRepository
) : EverydayMissionsRepository {

    override suspend fun getEverydayMissions(): Flow<ResultContainer<EverydayMissionsListEntity>> {
        return healthDataRepository.getEverydayMissions()
            .map { container -> container.map { it.toEverydayMissionListEntity() } }
    }

    override suspend fun setEverydayMissionsCompletion(mission: EverydayMissionEntity) {
        healthDataRepository.setMissionCompletion(mission.toEverydayMissionDataEntity())
    }

}