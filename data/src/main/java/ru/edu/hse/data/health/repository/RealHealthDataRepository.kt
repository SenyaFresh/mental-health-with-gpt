package ru.edu.hse.data.health.repository

import androidx.activity.result.contract.ActivityResultContract
import ru.edu.hse.data.health.entities.HealthDataEntity
import ru.edu.hse.data.health.sources.HealthDataSource
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class RealHealthDataRepository @Inject constructor(
    private val healthDataSource: HealthDataSource
) : HealthDataRepository {

    private val neutralMissions = listOf(
        "Подумайте о десяти событиях, которые произошли с вами на прошлой неделе, за которые вы чувствуете благодарность, и объясните самому себе, почему они принесли вам радость.",
        "Спланируйте и приготовьте один полноценный здоровый приём пищи сегодня. Используйте много овощей и зелени!",
        "Выполните серию упражнений на прогрессивное мышечное расслабление, начиная с стоп и заканчивая мускулами лица.",
        "Проведите как минимум 15 минут в общении с человеком, который вас вдохновляет или поднимает настроение.",
        "Совершите одно доброе дело без ожидания благодарности. Например, уступите место в транспорте или улыбнитесь незнакомцу.",
    )

    private val stepsMission =
        "Сегодня постарайтесь пройти 5,000 шагов. Каждый шаг приближает вас к здоровью!"

    private val heartRateMission =
        "Посвятите десять минут времени медитации, чтобы помочь поддерживать ваш сердечный ритм в нормальном состоянии"

    private val bloodPressureMission =
        "Посвятите десять минут времени йоге, чтобы помочь понизить ваше давление."

    override val permissions: Set<String>
        get() = healthDataSource.permissions

    override fun checkInstalled(): Boolean = healthDataSource.checkInstalled()

    override suspend fun hasAllPermissions(): Boolean = healthDataSource.hasAllPermissions()

    override fun requestPermissionActivityContract(): ActivityResultContract<Set<String>, Set<String>> =
        healthDataSource.requestPermissionActivityContract()

    override suspend fun getHealthData(): HealthDataEntity {
        val startOfDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant()
        val now = Instant.now()
        return healthDataSource.getHealthData(startOfDay, now)
    }

    override suspend fun getEverydayMission(): String {
        val startOfDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant()
        val startOfPreviousDay = startOfDay.minus(1, ChronoUnit.DAYS)

        val missions = neutralMissions.toMutableList()

        if (checkInstalled() && hasAllPermissions()) {

            val previousDayHealthData =
                healthDataSource.getHealthData(startOfPreviousDay, startOfDay)

            if (previousDayHealthData.stepsCount != null && previousDayHealthData.stepsCount < 4000) {
                missions += stepsMission
            }

            if (previousDayHealthData.heartRateAvg != null && previousDayHealthData.heartRateAvg > 80) {
                missions += heartRateMission
            }

            if (previousDayHealthData.bloodPressureSystolicAvg != null &&
                previousDayHealthData.bloodPressureSystolicAvg > 140 &&
                previousDayHealthData.bloodPressureDiastolicAvg != null &&
                previousDayHealthData.bloodPressureDiastolicAvg > 90
            ) {
                missions += bloodPressureMission
            }

        }
        
        return missions[ZonedDateTime.now().dayOfMonth % missions.size]
    }
}