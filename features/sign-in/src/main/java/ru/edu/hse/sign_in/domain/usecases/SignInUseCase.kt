package ru.edu.hse.sign_in.domain.usecases

import ru.edu.hse.sign_in.domain.exceptions.EmptyEmailException
import ru.edu.hse.sign_in.domain.exceptions.EmptyPasswordException
import ru.edu.hse.sign_in.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthenticationRepository
) {

    /**
     * Sign in user by email and password.
     * Throws exceptions if arguments are blank.
     */
    suspend fun signIn(email: String, password: String) {
        if (email.isBlank()) throw EmptyEmailException()
        if (password.isBlank()) throw EmptyPasswordException()

        authRepository.signIn(email, password)
    }

}