package ru.edu.hse.data

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.edu.hse.common.AppException
import ru.edu.hse.common.Core
import ru.edu.hse.data.health.entities.EverydayMissionDataEntity
import ru.edu.hse.data.health.entities.EverydayMissionsListDataEntity
import ru.edu.hse.data.health.utils.EverydayMissionDataEntityDeserializer
import ru.edu.hse.data.mental.test.entities.MentalTestDataEntity
import ru.edu.hse.data.mental.test.exceptions.MentalTestRepositoryException

object RemoteConfigManager {

    private val logger = Core.logger
    private val gson = GsonBuilder().registerTypeAdapter(
        EverydayMissionDataEntity::class.java,
        EverydayMissionDataEntityDeserializer()
    ).create()

    private val firebaseRemoteConfig: FirebaseRemoteConfig by lazy {
        Firebase.remoteConfig.apply {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 5
            }
            setConfigSettingsAsync(configSettings)
        }
    }

    private val _remoteConfigUpdates = MutableStateFlow<String?>(null)
    val remoteConfigUpdates = _remoteConfigUpdates.asStateFlow()

    fun fetchRemoteConfig() {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _remoteConfigUpdates.value = "remoteConfig:updated"
                logger.log("remoteConfigUpdate:successful")
            } else {
                _remoteConfigUpdates.value = null
                logger.log("remoteConfigUpdate:failure")
            }
        }
    }

    fun getMentalTest(): MentalTestDataEntity {
        val json = firebaseRemoteConfig.getString("mentalTest")
        try {
            val mentalTest = gson.fromJson(json, MentalTestDataEntity::class.java)
            logger.log("getMentalTest:success")
            return mentalTest
        } catch (e: Exception) {
            logger.logError(e, "remoteConfigUpdate:failure")
            throw MentalTestRepositoryException()
        }
    }

    fun getEverydayMissionsList(uid: String): EverydayMissionsListDataEntity {
        val json = firebaseRemoteConfig.getString(uid)
        try {
            val missions = gson.fromJson(json, EverydayMissionsListDataEntity::class.java)
            logger.log("getEverydayMissionsList:success")
            return missions
        } catch (e: Exception) {
            logger.logError(e, "getEverydayMissionsList:failure")
            throw MentalTestRepositoryException()
        }
    }

    fun getChatGPTApiKey(): String {
        try {
            val key = firebaseRemoteConfig.getString("chatGPTApiKey")
            logger.log("getChatGPTApiKey:success")
            return key
        } catch (e: Exception) {
            logger.logError(e, "getChatGPTApiKey:failure")
            throw AppException()
        }
    }
}