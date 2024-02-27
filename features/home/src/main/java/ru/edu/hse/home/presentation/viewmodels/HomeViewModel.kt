package ru.edu.hse.home.presentation.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.DepressionTest
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
    private val getDepressionTestUseCase: GetDepressionTestUseCase,
    private val getDepressionPointsUseCase: GetDepressionPointsUseCase,
    private val getEverydayMissionUseCase: GetEverydayMissionUseCase,
    private val getHealthDataUseCase: GetHealthDataUseCase,
    private val updateDepressionPointsUseCase: UpdateDepressionPointsUseCase,
    getPermissionsUseCase: GetPermissionsUseCase
) : BaseViewModel() {

    val permissions = getPermissionsUseCase.getSetOfPermissions()
    val permissionsLauncher = getPermissionsUseCase.requestPermissionActivityContract()

    private val _healthDataStateFlow =
        MutableStateFlow<ResultContainer<HealthData>>(ResultContainer.Pending)
    val healthDataStateFlow = _healthDataStateFlow.asStateFlow()

    private val _everydayMissionStateFlow =
        MutableStateFlow<ResultContainer<String>>(ResultContainer.Pending)
    val everydayMissionStateFlow = _everydayMissionStateFlow.asStateFlow()

    private val _depressionStatsStateFlow =
        MutableStateFlow<ResultContainer<Int>>(ResultContainer.Pending)
    val depressionStatsStateFlow = _depressionStatsStateFlow.asStateFlow()

    private val _depressionTestStateFlow =
        MutableStateFlow<ResultContainer<DepressionTest>>(ResultContainer.Pending)
    val depressionsTestStateFlow = _depressionTestStateFlow.asStateFlow()

    init {
        loadHealthData()
        loadEverydayMission()
        loadDepressionStats()
        loadDepressionTest()
    }

    private fun loadHealthData() = viewModelScope.launch {
        try {
            _healthDataStateFlow.value = ResultContainer.Pending
            _healthDataStateFlow.value =
                ResultContainer.Success(getHealthDataUseCase.getHealthData())
        } catch (e: Exception) {
            _healthDataStateFlow.value = ResultContainer.Error(e)
        }
    }

    fun loadHealthDataWithDebounce() = debounce { loadHealthData() }

    private fun loadEverydayMission() = viewModelScope.launch {
        try {
            _everydayMissionStateFlow.value = ResultContainer.Pending
            _everydayMissionStateFlow.value =
                ResultContainer.Success(getEverydayMissionUseCase.getEveryDayMission())
        } catch (e: Exception) {
            _everydayMissionStateFlow.value = ResultContainer.Error(e)
        }
    }

    fun loadEverydayMissionWithDebounce() = debounce { loadEverydayMission() }

    private fun loadDepressionStats() = viewModelScope.launch {
        getDepressionPointsUseCase.getDepressionPoints().collect {
            _depressionStatsStateFlow.value = it
        }
    }

    private fun loadDepressionTest() = viewModelScope.launch {
        getDepressionTestUseCase.getDepressionTest().collect {
            _depressionTestStateFlow.value = it
        }
    }

    fun updateDepressionPoints(points: Int) {
        viewModelScope.launch {
            updateDepressionPointsUseCase.updateDepressionPoints(points)
        }
    }
}