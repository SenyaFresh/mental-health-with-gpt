package ru.edu.hse.profile.domain.usecases

import ru.edu.hse.profile.domain.entities.Profile
import ru.edu.hse.profile.domain.exceptions.EmptyEmailException
import ru.edu.hse.profile.domain.exceptions.EmptyUsernameException
import ru.edu.hse.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend fun editProfile(profile: Profile) {
        if (profile.email.isBlank()) throw EmptyEmailException()
        if (profile.username.isBlank()) throw EmptyUsernameException()

        profileRepository.editProfile(profile)
    }
}