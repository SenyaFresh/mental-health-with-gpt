package ru.edu.hse.data.health.sources

import android.content.Context
import androidx.activity.result.contract.ActivityResultContract
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.HealthConnectClient.Companion.SDK_AVAILABLE
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.Core
import ru.edu.hse.data.health.entities.HealthDataEntity
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class HealthConnectHealthDataSource @Inject constructor(@ApplicationContext private val context: Context) :
    HealthDataSource {

    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    private val auth = Firebase.auth

    private val db = Firebase.firestore

    private val logger = Core.logger

    override val permissions = setOf(
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(HeartRateRecord::class),
        HealthPermission.getReadPermission(SleepSessionRecord::class)
    )

    override fun checkInstalled(): Boolean {
        return HealthConnectClient.sdkStatus(context) == SDK_AVAILABLE
    }

    override suspend fun hasAllPermissions(): Boolean {
        return healthConnectClient.permissionController.getGrantedPermissions()
            .containsAll(permissions)
    }

    override fun requestPermissionActivityContract(): ActivityResultContract<Set<String>, Set<String>> {
        return PermissionController.createRequestPermissionResultContract()
    }

    override suspend fun getHealthData(): HealthDataEntity {
        if (fetchedData()) {
            for (i in (0..365)) {

            }
        }

        val healthData = HealthDataEntity(
            stepsCount = getStepsData(),
            heartRateAvg = getHeartRateData(),
            sleepMinutes = getSleepData()
        )

        return healthData
    }

    private suspend fun getStepsData() : Long? {
        val startOfDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant()
        val now = Instant.now()

        val stepsTimeRangeFilter = TimeRangeFilter.between(startOfDay, now)

        val stepsAggregateDataTypes = setOf(
            StepsRecord.COUNT_TOTAL,
        )
        val stepsAggregateRequest = AggregateRequest(
            metrics = stepsAggregateDataTypes,
            timeRangeFilter = stepsTimeRangeFilter
        )

        return healthConnectClient.aggregate(stepsAggregateRequest)[StepsRecord.COUNT_TOTAL]
    }

    private suspend fun getHeartRateData() : Long? {
        val startOfHour = ZonedDateTime.now().truncatedTo(ChronoUnit.HOURS).toInstant()
        val startOfPreviousHour = startOfHour.minus(1, ChronoUnit.HOURS)

        val heartRateTimeRangeFilter = TimeRangeFilter.between(startOfPreviousHour, startOfHour)

        val heartRateAggregateDataTypes = setOf(
            HeartRateRecord.BPM_AVG,
        )

        val heartRateAggregateRequest = AggregateRequest(
            metrics = heartRateAggregateDataTypes,
            timeRangeFilter = heartRateTimeRangeFilter
        )

        return healthConnectClient.aggregate(heartRateAggregateRequest)[HeartRateRecord.BPM_AVG]
    }

    private suspend fun getSleepData() : Long? {
        val startOfDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant()
        val previousDay6pm = startOfDay.minus(6, ChronoUnit.HOURS)
        val thisDay6pm = startOfDay.plus(18, ChronoUnit.HOURS)

        val sleepTimeRangeFilter = TimeRangeFilter.between(previousDay6pm, thisDay6pm)

        val sleepAggregateDataTypes = setOf(
            SleepSessionRecord.SLEEP_DURATION_TOTAL,
        )

        val sleepAggregateRequest = AggregateRequest(
            metrics = sleepAggregateDataTypes,
            timeRangeFilter = sleepTimeRangeFilter
        )

        return healthConnectClient.aggregate(sleepAggregateRequest)[SleepSessionRecord.SLEEP_DURATION_TOTAL]?.toMinutes()
    }

    private suspend fun fetchedData() : Boolean {
        val db = Firebase.firestore
        val auth = Firebase.auth
        return try {
            val document = db.collection(USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .get()
                .await()
            document[KEY_FETCHED_DATA] as Boolean
        } catch (e: Exception) {
            logger.log("getFetchedData:failure")
            throw AuthenticationException()
        }
    }

    companion object {
        const val USERS_COLLECTION = "users"

        const val KEY_FETCHED_DATA = "fetchedData"

        const val STEPS_COLLECTION = "steps"
        const val HEART_RATE_COLLECTION = "heartRate"
        const val SLEEP_COLLECTION = "sleep"
    }

}