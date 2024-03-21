package ru.edu.hse.home.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.EverydayMissionEntity
import ru.edu.hse.home.domain.entities.EverydayMissionsListEntity

interface EverydayMissionsRepository {

    /**
     * Get everyday mission from repository.
     */
    suspend fun getEverydayMissions() : Flow<ResultContainer<EverydayMissionsListEntity>>

    /**
     * Set completion of mission.
     */
    suspend fun setEverydayMissionsCompletion(mission: EverydayMissionEntity)

}