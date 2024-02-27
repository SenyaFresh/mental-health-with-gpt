package ru.edu.hse.home.domain.entities

data class HealthData (
    val stepsCount: Long?,
    val heartRateAvg: Long?,
    val bloodPressureSystolicAvg: Long?,
    val bloodPressureDiastolicAvg: Long?
)