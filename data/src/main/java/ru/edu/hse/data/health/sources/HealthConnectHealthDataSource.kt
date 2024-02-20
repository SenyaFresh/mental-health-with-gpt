package ru.edu.hse.data.health.sources

import android.content.Context
import androidx.activity.result.contract.ActivityResultContract
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.HealthConnectClient.Companion.SDK_AVAILABLE
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.time.TimeRangeFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.edu.hse.data.health.entities.HealthDataEntity
import java.time.Instant
import javax.inject.Inject

class HealthConnectHealthDataSource @Inject constructor(@ApplicationContext private val context: Context) :
    HealthDataSource {

    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    override val permissions = setOf(
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(HeartRateRecord::class),
        HealthPermission.getReadPermission(BloodPressureRecord::class)
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

    override suspend fun getHealthData(startTime: Instant, finishTime: Instant): HealthDataEntity {
        val timeRangeFilter = TimeRangeFilter.between(startTime, finishTime)
        val aggregateDataTypes = setOf(
            StepsRecord.COUNT_TOTAL,
            HeartRateRecord.BPM_AVG,
            BloodPressureRecord.SYSTOLIC_AVG,
            BloodPressureRecord.DIASTOLIC_AVG
        )

        val aggregateRequest = AggregateRequest(
            metrics = aggregateDataTypes,
            timeRangeFilter = timeRangeFilter
        )

        val aggregateData = healthConnectClient.aggregate(aggregateRequest)

        return HealthDataEntity(
            stepsCount = aggregateData[StepsRecord.COUNT_TOTAL],
            heartRateAvg = aggregateData[HeartRateRecord.BPM_AVG],
            bloodPressureDiastolicAvg = aggregateData[BloodPressureRecord.DIASTOLIC_AVG]?.inMillimetersOfMercury?.toLong(),
            bloodPressureSystolicAvg = aggregateData[BloodPressureRecord.SYSTOLIC_AVG]?.inMillimetersOfMercury?.toLong()
        )
    }

}