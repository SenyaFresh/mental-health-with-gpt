package ru.edu.hse.profile.presentation.events

import ru.edu.hse.profile.domain.entities.Profile

sealed class ProfileEvent {
    /**
     * Edit users email and name.
     */
    data class EditProfile(val profile: Profile) : ProfileEvent()

    /**
     * Log out user from app.
     */
    data object Logout : ProfileEvent()

    /**
     * Disable email error.
     */
    data object DisableEmailError : ProfileEvent()

    /**
     * Disable username error.
     */
    data object DisableUsernameError : ProfileEvent()
}