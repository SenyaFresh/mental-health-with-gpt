package ru.edu.hse.profile.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.profile.domain.entities.Profile

interface ProfileRepository{

    /**
     * Get user profile.
     */
    fun getProfile() : Flow<ResultContainer<Profile>>

    /**
     * Edit user profile.
     */
    suspend fun editProfile(profile: Profile)

    /**
     * Reload profile user (reload flow returned by [getProfile]).
     */
    fun reloadProfile()

    /**
     * Log out from app.
     */
    suspend fun logout()
}