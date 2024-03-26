package ru.edu.hse.sign_up.domain.usecases

import ru.edu.hse.sign_up.domain.exceptions.EmptyEmailException
import ru.edu.hse.sign_up.domain.exceptions.EmptyPasswordException
import ru.edu.hse.sign_up.domain.exceptions.EmptyUsernameException
import ru.edu.hse.sign_up.domain.repositories.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {

    /**
     * Register user in repository.
     */
    suspend fun signUp(email: String, username: String, password: String) {
        if (email.isBlank()) throw EmptyEmailException()
        if (username.isBlank()) throw EmptyUsernameException()
        if (password.isBlank()) throw EmptyPasswordException()

        signUpRepository.signUp(email, username, password)
    }

}