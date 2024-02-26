package ru.edu.hse.home.presentation.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.HealthData
import ru.edu.hse.home.domain.usecases.GetDepressionPointsUseCase
import ru.edu.hse.home.domain.usecases.GetDepressionTestUseCase
import ru.edu.hse.home.domain.usecases.GetEverydayMissionUseCase
import ru.edu.hse.home.domain.usecases.GetHealthDataUseCase
import ru.edu.hse.home.domain.usecases.GetPermissionsUseCase
import ru.edu.hse.home.domain.usecases.UpdateDepressionPointsUseCase
import ru.edu.hse.presentation.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    getDepressionTestUseCase: GetDepressionTestUseCase,
    getDepressionPointsUseCase: GetDepressionPointsUseCase,
    private val getEverydayMissionUseCase: GetEverydayMissionUseCase,
    private val getHealthDataUseCase: GetHealthDataUseCase,
    private val getPermissionsUseCase: GetPermissionsUseCase,
    private val updateDepressionPointsUseCase: UpdateDepressionPointsUseCase
) : BaseViewModel(){

    val permissions = getPermissionsUseCase.getSetOfPermissions()
    val permissionsLauncher = getPermissionsUseCase.requestPermissionActivityContract()

    private val healthDataStateFlow = MutableStateFlow<ResultContainer<HealthData>>(ResultContainer.Pending)
    private val everydayMissionStateFlow = MutableStateFlow<ResultContainer<String>>(ResultContainer.Pending)

    val depressionStatsState = getDepressionPointsUseCase.getDepressionPoints().shareIn(viewModelScope, SharingStarted.Eagerly)
    val depressionTestState = getDepressionTestUseCase.getDepressionTest().shareIn(viewModelScope, SharingStarted.Eagerly)

    init {
        loadEverydayMission()
        loadHealthData()
    }

    fun loadHealthData() = debounce {
        viewModelScope.launch {
            try {
                healthDataStateFlow.value = ResultContainer.Pending
                healthDataStateFlow.value = ResultContainer.Success(getHealthDataUseCase.getHealthData())
            } catch (e: Exception) {
                healthDataStateFlow.value = ResultContainer.Error(e)
            }
        }
    }

    fun loadEverydayMission() = debounce {
        viewModelScope.launch {
            try {
                everydayMissionStateFlow.value = ResultContainer.Pending
                everydayMissionStateFlow.value = ResultContainer.Success(getEverydayMissionUseCase.getEveryDayMission())
            } catch (e: Exception) {
                everydayMissionStateFlow.value = ResultContainer.Error(e)
            }
        }
    }

    fun updateDepressionPoints(points: Int) {
        viewModelScope.launch {
            updateDepressionPointsUseCase.updateDepressionPoints(points)
        }
    }
}