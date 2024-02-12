package ru.edu.hse.sign_in.presentation.events

sealed class SignInEvent {

    /**
     * Sign in user by [email] and [password].
     */
    data class SignIn(val email: String, val password: String) : SignInEvent()

    /**
     * Launch sign up screen.
     */
    data object LaunchSignUp : SignInEvent()

    /**
     * Disable email error.
     */
    data object DisableEmailError : SignInEvent()

    /**
     * Disable password error.
     */
    data object DisablePasswordError : SignInEvent()
}