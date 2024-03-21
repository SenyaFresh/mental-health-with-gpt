package ru.edu.hse.home.presentation.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.EverydayMissionsListEntity
import ru.edu.hse.home.domain.entities.HealthData
import ru.edu.hse.home.domain.entities.MentalTestEntity
import ru.edu.hse.home.domain.usecases.GetEverydayMissionsUseCase
import ru.edu.hse.home.domain.usecases.GetHealthDataUseCase
import ru.edu.hse.home.domain.usecases.GetMentalTestUseCase
import ru.edu.hse.home.domain.usecases.GetPermissionsUseCase
import ru.edu.hse.home.domain.usecases.SetMissionCompletionUseCase
import ru.edu.hse.presentation.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getMentalTestUseCase: GetMentalTestUseCase,
    private val getEverydayMissionsUseCase: GetEverydayMissionsUseCase,
    private val setMissionCompletionUseCase: SetMissionCompletionUseCase,
    private val getHealthDataUseCase: GetHealthDataUseCase,
    getPermissionsUseCase: GetPermissionsUseCase
) : BaseViewModel() {

    val permissions = getPermissionsUseCase.getSetOfPermissions()
    val permissionsLauncher = getPermissionsUseCase.requestPermissionActivityContract()

    private val _healthDataStateFlow =
        MutableStateFlow<ResultContainer<HealthData>>(ResultContainer.Pending)
    val healthDataStateFlow = _healthDataStateFlow.asStateFlow()

    private val _everydayMissionStateFlow =
        MutableStateFlow<ResultContainer<EverydayMissionsListEntity>>(ResultContainer.Pending)
    val everydayMissionStateFlow = _everydayMissionStateFlow.asStateFlow()

    private val _mentalTestStateFlow =
        MutableStateFlow<ResultContainer<MentalTestEntity>>(ResultContainer.Pending)
    val mentalTestStateFlow = _mentalTestStateFlow.asStateFlow()

    init {
        collectHealthData()
        collectEverydayMissions()
        collectMentalTest()
    }

    private fun collectHealthData() {
        viewModelScope.launch {
            getHealthDataUseCase.getHealthData().collect { result ->
                _healthDataStateFlow.value = result
            }
        }
    }

    private fun collectEverydayMissions() {
        viewModelScope.launch {
            getEverydayMissionsUseCase.getEverydayMissions().collect { result ->
                _everydayMissionStateFlow.value = result
            }
        }
    }

    private fun collectMentalTest() {
        viewModelScope.launch {
            getMentalTestUseCase.getMentalTest().collect { result ->
                _mentalTestStateFlow.value = result
            }
        }
    }

}