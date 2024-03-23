package ru.edu.hse.mentalhealthwithgpt.glue.home.mappers

import ru.edu.hse.data.health.entities.EverydayMissionDataEntity
import ru.edu.hse.data.health.entities.EverydayMissionsListDataEntity
import ru.edu.hse.data.health.entities.HealthDataEntity
import ru.edu.hse.home.domain.entities.EverydayMissionEntity
import ru.edu.hse.home.domain.entities.EverydayMissionsListEntity
import ru.edu.hse.home.domain.entities.HealthData


fun HealthDataEntity.toHealthData(): HealthData {
    return HealthData(stepsCount, heartRateAvg, sleepMinutes)
}

fun EverydayMissionsListDataEntity.toEverydayMissionListEntity(): EverydayMissionsListEntity {
    return EverydayMissionsListEntity(missionsList.map { it.toEverydayMissionEntity() })
}

fun EverydayMissionDataEntity.toEverydayMissionEntity(): EverydayMissionEntity {
    return EverydayMissionEntity(text, completed)
}

fun EverydayMissionEntity.toEverydayMissionDataEntity(): EverydayMissionDataEntity {
    return EverydayMissionDataEntity(text, completed)
}
