package ru.edu.hse.profile.domain.usecases

import ru.edu.hse.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    /**
     * Log out from app.
     */
    suspend fun logout() {
        profileRepository.logout()
    }

}