package ru.edu.hse.home.presentation.events

import ru.edu.hse.home.domain.entities.EverydayMissionEntity
import ru.edu.hse.home.domain.entities.MentalTestQuestionEntity

sealed class HomeEvent {

    data class SetMentalTestAnswerEvent(val question: MentalTestQuestionEntity, val answer: String) : HomeEvent()

    data class SetMissionCompletionEvent(val mission: EverydayMissionEntity) : HomeEvent()

    data object HealthOnLoad: HomeEvent()

    data object EverydayMissionsOnLoad: HomeEvent()

    data object MentalTestOnLoad: HomeEvent()
}