package ru.edu.hse.data.health.entities

import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

data class EverydayMissionDataEntity(
    val text: String,
    val completed: Boolean = false,
    val date: String = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant().toString()
)