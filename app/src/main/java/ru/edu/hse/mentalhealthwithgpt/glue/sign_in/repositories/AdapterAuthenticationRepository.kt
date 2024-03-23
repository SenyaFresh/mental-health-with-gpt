package ru.edu.hse.mentalhealthwithgpt.glue.sign_in.repositories

import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.unwrapFirstNotPending
import ru.edu.hse.data.accounts.repository.AccountsDataRepository
import ru.edu.hse.sign_in.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class AdapterAuthenticationRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository
) : AuthenticationRepository {

    override suspend fun isSignedIn(): Boolean {
        return try {
            accountsDataRepository.getAccount().unwrapFirstNotPending()
            true
        } catch (e: AuthenticationException) {
            false
        }
    }

    override suspend fun signIn(email: String, password: String) {
        accountsDataRepository.signIn(email, password)
    }

}