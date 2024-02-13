package ru.edu.hse.sign_up.presentation.events

sealed class SignUpEvent {
    /**
     * Sign up user by [email], [username] and [password].
     */
    data class SignUp(val email: String, val username: String, val password: String) : SignUpEvent()

    /**
     * Disable email error.
     */
    data object DisableEmailError : SignUpEvent()

    /**
     * Disable username error.
     */
    data object DisableUsernameError : SignUpEvent()

    /**
     * Disable password error.
     */
    data object DisablePasswordError : SignUpEvent()
}