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
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.Core
import ru.edu.hse.data.RemoteConfigManager
import ru.edu.hse.data.health.entities.EverydayMissionDataEntity
import ru.edu.hse.data.health.entities.EverydayMissionsListDataEntity
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
        if (!getFetchedDataFlag()) {
            setPastYearData()
        }

        val healthData = HealthDataEntity(
            stepsCount = getStepsData(),
            heartRateAvg = getHeartRateData(),
            sleepMinutes = getSleepData()
        )
        setStepsDataForLastDays(1)
        setHeartRateDataForLastHours(1)
        setSleepDataForLastDays(1)

        return healthData
    }

    override suspend fun getEverydayMissions(): EverydayMissionsListDataEntity {
        var missions = RemoteConfigManager.getEverydayMissionsList(auth.currentUser!!.uid)
        if (missions.missionsList.isEmpty()) return missions
        try {
            val documentSnapshot = db.collection(USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .collection(MISSIONS_COLLECTION)
                .document(missions.missionsList[0].date)
                .get()
                .await()

            val missionsFbEntity = documentSnapshot.data ?: emptyMap()

            val newMissions = missions.missionsList.map { mission ->
                mission.copy(completed = missionsFbEntity[mission.text] as? Boolean ?: false)
            }

            missions = missions.copy(missionsList = newMissions)
            logger.log("getMissionsList:success")
            return missions
        } catch (e: NullPointerException) {
            logger.logError(e, "getMissionsList:empty")
            return missions
        }
        catch (e: Exception) {
            logger.logError(e, "getMissionsList:failure")
            throw AuthenticationException()
        }
    }

    override suspend fun setMissionCompletion(mission: EverydayMissionDataEntity) {
        try {
            db.collection(USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .collection(MISSIONS_COLLECTION)
                .document(mission.date)
                .set(hashMapOf<String, Any>(mission.text to mission.completed))
                .await()

            logger.log("setMissionCompletion:success")
        } catch (e: Exception) {
            logger.logError(e, "setMissionCompletion:failure")
            throw AuthenticationException()
        }
    }

    private suspend fun setPastYearData() {
        setStepsDataForLastDays(365)
        setHeartRateDataForLastHours(365 * 24)
        setSleepDataForLastDays(365)
        try {
            db.collection(USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .set(hashMapOf<String, Any>(KEY_FETCHED_DATA to true), SetOptions.merge())
                .await()
            logger.log("setFetchedDataFlag:success")
        } catch (e: Exception) {
            logger.logError(e, "setFetchedDataFlag:failure")
            throw AuthenticationException()
        }
    }

    private suspend fun setStepsDataForLastDays(days: Long) {
        val stepsRecordData = HashMap<String, Any>()
        for (i in (0 until days)) {
            val startOfDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant()
                .minus(i, ChronoUnit.DAYS)
            val startOfPreviousDay = startOfDay.minus(1, ChronoUnit.DAYS)
            stepsRecordData["$startOfPreviousDay-$startOfDay"] =
                getStepsData(startOfPreviousDay, startOfDay) ?: 0
        }
        setStepsData(stepsRecordData)
    }

    private suspend fun setStepsData(stepsRecordData: HashMap<String, Any>) {
        try {
            db.collection(USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .collection(HEALTH_COLLECTION)
                .document(STEPS_KEY)
                .set(stepsRecordData, SetOptions.merge())
                .await()
            logger.log("setStepsData:success")
        } catch (e: Exception) {
            logger.logError(e, "setStepsData:failure")
            throw AuthenticationException()
        }
    }

    private suspend fun setHeartRateDataForLastHours(hours: Long) {
        val heartRateRecordData = HashMap<String, Any>()
        for (i in (0 until hours)) {
            val endInstant = ZonedDateTime.now().truncatedTo(ChronoUnit.HOURS).toInstant()
                .minus(i, ChronoUnit.HOURS)
            val startInstant = endInstant.minus(1, ChronoUnit.HOURS)
            heartRateRecordData["$startInstant-$endInstant"] =
                getHeartRateData(startInstant, endInstant) ?: 0
        }
        setHeartRateData(heartRateRecordData)
    }

    private suspend fun setHeartRateData(heartRateRecordData: HashMap<String, Any>) {
        try {
            db.collection(USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .collection(HEALTH_COLLECTION)
                .document(HEART_RATE_KEY)
                .set(heartRateRecordData, SetOptions.merge())
                .await()
            logger.log("setHeartRateData:success")
        } catch (e: Exception) {
            logger.logError(e, "setHeartRateData:failure")
            throw AuthenticationException()
        }
    }

    private suspend fun setSleepDataForLastDays(days: Long) {
        val sleepRecordData = HashMap<String, Any>()
        for (i in (0 until days)) {
            val startOfDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant()
                .minus(i, ChronoUnit.DAYS)
            val startInstant = startOfDay.minus(6, ChronoUnit.HOURS)
            val endInstant = startOfDay.plus(18, ChronoUnit.HOURS)
            sleepRecordData["$startInstant-$endInstant"] =
                getSleepData(startInstant, endInstant) ?: 0
        }
        setSleepData(sleepRecordData)
    }

    private suspend fun setSleepData(sleepRecordData: HashMap<String, Any>) {
        try {
            db.collection(USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .collection(HEALTH_COLLECTION)
                .document(SLEEP_KEY)
                .set(sleepRecordData, SetOptions.merge())
                .await()
            logger.log("setSleepData:success")
        } catch (e: Exception) {
            logger.logError(e, "setSleepData:failure")
            throw AuthenticationException()
        }
    }

    private suspend fun getStepsData(start: Instant? = null, end: Instant? = null): Long? {
        val startInstant = start ?: ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant()
        val endInstant = end ?: Instant.now()

        val stepsTimeRangeFilter = TimeRangeFilter.between(startInstant, endInstant)

        val stepsAggregateDataTypes = setOf(
            StepsRecord.COUNT_TOTAL,
        )
        val stepsAggregateRequest = AggregateRequest(
            metrics = stepsAggregateDataTypes,
            timeRangeFilter = stepsTimeRangeFilter
        )

        return healthConnectClient.aggregate(stepsAggregateRequest)[StepsRecord.COUNT_TOTAL]
    }

    private suspend fun getHeartRateData(start: Instant? = null, end: Instant? = null): Long? {
        val startInstant = start ?: ZonedDateTime.now().truncatedTo(ChronoUnit.HOURS).toInstant()
        val endInstant = end ?: startInstant.minus(1, ChronoUnit.HOURS)

        val heartRateTimeRangeFilter = TimeRangeFilter.between(endInstant, startInstant)

        val heartRateAggregateDataTypes = setOf(
            HeartRateRecord.BPM_AVG,
        )

        val heartRateAggregateRequest = AggregateRequest(
            metrics = heartRateAggregateDataTypes,
            timeRangeFilter = heartRateTimeRangeFilter
        )

        return healthConnectClient.aggregate(heartRateAggregateRequest)[HeartRateRecord.BPM_AVG]
    }

    private suspend fun getSleepData(start: Instant? = null, end: Instant? = null): Long? {
        val startOfDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant()
        val startInstant = start ?: startOfDay.minus(6, ChronoUnit.HOURS)
        val endInstant = end ?: startOfDay.plus(18, ChronoUnit.HOURS)

        val sleepTimeRangeFilter = TimeRangeFilter.between(startInstant, endInstant)

        val sleepAggregateDataTypes = setOf(
            SleepSessionRecord.SLEEP_DURATION_TOTAL,
        )

        val sleepAggregateRequest = AggregateRequest(
            metrics = sleepAggregateDataTypes,
            timeRangeFilter = sleepTimeRangeFilter
        )

        return healthConnectClient.aggregate(sleepAggregateRequest)[SleepSessionRecord.SLEEP_DURATION_TOTAL]?.toMinutes()
    }

    private suspend fun getFetchedDataFlag(): Boolean {
        val db = Firebase.firestore
        val auth = Firebase.auth
        return try {
            val document = db.collection(USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .get()
                .await()
            logger.log("getFetchedDataFlag:success")
            document[KEY_FETCHED_DATA] as Boolean
        } catch (e: Exception) {
            logger.logError(e, "getFetchedDataFlag:failure")
            throw AuthenticationException()
        }
    }

    companion object {
        const val USERS_COLLECTION = "users"

        const val KEY_FETCHED_DATA = "fetchedDataFlag"

        const val HEALTH_COLLECTION = "health"
        const val MISSIONS_COLLECTION = "missions"

        const val STEPS_KEY = "steps"
        const val HEART_RATE_KEY = "heartRate"
        const val SLEEP_KEY = "sleep"
    }

}