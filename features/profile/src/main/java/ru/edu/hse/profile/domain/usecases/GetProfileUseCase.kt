package ru.edu.hse.profile.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.profile.domain.entities.Profile
import ru.edu.hse.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    /**
     * Get user profile
     */
    fun getProfile() : Flow<ResultContainer<Profile>> {
        return profileRepository.getProfile()
    }

    /**
     * Reload profile user (reload flow returned by [getProfile]).
     */
    fun reloadProfile() {
        profileRepository.reloadProfile()
    }
}