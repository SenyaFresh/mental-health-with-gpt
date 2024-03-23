package ru.edu.hse.mentalhealthwithgpt.glue.profile.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.data.accounts.repository.AccountsDataRepository
import ru.edu.hse.mentalhealthwithgpt.glue.profile.mappers.toProfile
import ru.edu.hse.profile.domain.entities.Profile
import ru.edu.hse.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class AdapterProfileRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository,
) : ProfileRepository {

    override fun getProfile(): Flow<ResultContainer<Profile>> {
        return accountsDataRepository.getAccount()
            .map { container -> container.map { it.toProfile() } }
    }

    override suspend fun editProfile(profile: Profile) {
        accountsDataRepository.updateAccountUsernameAndEmail(profile.username, profile.email)
    }

    override fun reloadProfile() {
        accountsDataRepository.reload()
    }

    override suspend fun logout() {
        accountsDataRepository.logout()
    }
}