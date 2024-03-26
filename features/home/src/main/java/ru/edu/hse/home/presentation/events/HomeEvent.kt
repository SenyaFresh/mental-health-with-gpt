package ru.edu.hse.home.presentation.events

import ru.edu.hse.home.domain.entities.EverydayMissionEntity
import ru.edu.hse.home.domain.entities.MentalTestQuestionEntity

sealed class HomeEvent {

    /**
     * Set answer for mental test question.
     */
    data class SetMentalTestAnswerEvent(val question: MentalTestQuestionEntity, val answer: String) : HomeEvent()

    /**
     * Change mission completion status.
     */
    data class SetMissionCompletionEvent(val mission: EverydayMissionEntity) : HomeEvent()

    /**
     * Load health data.
     */
    data object HealthOnLoad: HomeEvent()

    /**
     * Load everyday missions.
     */
    data object EverydayMissionsOnLoad: HomeEvent()

    /**
     * Load mental test.
     */
    data object MentalTestOnLoad: HomeEvent()

}