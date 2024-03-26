package ru.edu.hse.sign_in.domain.usecases

import ru.edu.hse.sign_in.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class CheckIfSignedInUseCase @Inject constructor(
    private val authRepository: AuthenticationRepository
) {

    /**
     * Check if user signed in.
     */
    suspend fun isSignedIn(): Boolean = authRepository.isSignedIn()

}