package ru.edu.hse.home.domain.repositories

interface EverydayMissionRepository {

    /**
     * Get everyday mission from repository.
     */
    suspend fun getEverydayMission() : String

}