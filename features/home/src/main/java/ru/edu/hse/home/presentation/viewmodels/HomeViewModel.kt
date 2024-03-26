package ru.edu.hse.home.presentation.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.EverydayMissionEntity
import ru.edu.hse.home.domain.entities.EverydayMissionsListEntity
import ru.edu.hse.home.domain.entities.HealthData
import ru.edu.hse.home.domain.entities.MentalTestEntity
import ru.edu.hse.home.domain.entities.MentalTestQuestionEntity
import ru.edu.hse.home.domain.usecases.GetEverydayMissionsUseCase
import ru.edu.hse.home.domain.usecases.GetHealthDataUseCase
import ru.edu.hse.home.domain.usecases.GetMentalTestUseCase
import ru.edu.hse.home.domain.usecases.GetPermissionsUseCase
import ru.edu.hse.home.domain.usecases.SetMentalTestAnswerUseCase
import ru.edu.hse.home.domain.usecases.SetMissionCompletionUseCase
import ru.edu.hse.home.presentation.events.HomeEvent
import ru.edu.hse.presentation.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMentalTestUseCase: GetMentalTestUseCase,
    private val setMentalTestAnswerUseCase: SetMentalTestAnswerUseCase,
    private val getEverydayMissionsUseCase: GetEverydayMissionsUseCase,
    private val setMissionCompletionUseCase: SetMissionCompletionUseCase,
    private val getHealthDataUseCase: GetHealthDataUseCase,
    getPermissionsUseCase: GetPermissionsUseCase
) : BaseViewModel() {

    val permissions = getPermissionsUseCase.getSetOfPermissions()
    val permissionsLauncher = getPermissionsUseCase.requestPermissionActivityContract()

    private val _healthDataStateFlow =
        MutableStateFlow<ResultContainer<HealthData>>(ResultContainer.Loading)
    val healthDataStateFlow = _healthDataStateFlow.asStateFlow()

    private val _everydayMissionsStateFlow =
        MutableStateFlow<ResultContainer<EverydayMissionsListEntity>>(ResultContainer.Loading)
    val everydayMissionsStateFlow = _everydayMissionsStateFlow.asStateFlow()

    private val _mentalTestStateFlow =
        MutableStateFlow<ResultContainer<MentalTestEntity>>(ResultContainer.Loading)
    val mentalTestStateFlow = _mentalTestStateFlow.asStateFlow()

    init {
        collectHealthData()
        collectEverydayMissions()
        collectMentalTest()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SetMissionCompletionEvent -> setMissionCompletion(event.mission)
            is HomeEvent.SetMentalTestAnswerEvent -> setMentalTestAnswer(event.question, event.answer)
            is HomeEvent.HealthOnLoad -> debounce { collectHealthData() }
            is HomeEvent.EverydayMissionsOnLoad -> debounce { collectEverydayMissions() }
            is HomeEvent.MentalTestOnLoad -> debounce { collectEverydayMissions() }
        }
    }

    fun reloadHealthData() {
        getHealthDataUseCase.reloadHealthData()
    }

    private fun setMissionCompletion(mission: EverydayMissionEntity) = debounce {
        viewModelScope.launch {
            setMissionCompletionUseCase.setEverydayMissionsCompletion(mission)
        }
    }

    private fun setMentalTestAnswer(question: MentalTestQuestionEntity, answer: String) = debounce {
        viewModelScope.launch {
            setMentalTestAnswerUseCase.setMentalTestAnswer(question, answer)
        }
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
                _everydayMissionsStateFlow.value = result
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