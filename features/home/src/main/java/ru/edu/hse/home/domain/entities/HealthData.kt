package ru.edu.hse.home.domain.entities

data class HealthData (
    val stepsCount: Long?,
    val heartRateAvg: Long?,
    val bloodPressureDiastolicAvg: Long?,
    val bloodPressureSystolicAvg: Long?
)