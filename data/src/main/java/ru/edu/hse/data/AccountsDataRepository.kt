package ru.edu.hse.data

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.data.accounts.entities.AccountDataEntity
import ru.edu.hse.data.accounts.entities.SignUpDataEntity

interface AccountsDataRepository {

    /**
     * Log in to account.
     */
    suspend fun signIn(email: String, username: String)

    /**
     * Create new account.
     */
    suspend fun singUp(signUpDataEntity: SignUpDataEntity)

    /**
     * Update account [username] and [email].
     */
    suspend fun updateAccount(username: String, email: String)

    /**
     * Log out from account.
     */
    suspend fun logout()

    /**
     * Reload account.
     */
    fun reload()

    /**
     * Get infinite flow with [AccountDataEntity] wrapped in [ResultContainer].
     */
    fun getAccount(): Flow<ResultContainer<AccountDataEntity>>

}