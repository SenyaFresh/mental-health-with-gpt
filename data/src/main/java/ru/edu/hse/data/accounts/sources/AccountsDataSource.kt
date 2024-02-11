package ru.edu.hse.data.accounts.sources

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.data.accounts.entities.AccountDataEntity
import ru.edu.hse.data.accounts.entities.SignUpDataEntity

interface AccountsDataSource {

    /**
     * Listen for account changes and log out.
     */
    fun listenAuthState() : Flow<String?>

    /**
     * Log in to account.
     */
    suspend fun signIn(email: String, password: String)

    /**
     * Create new account.
     */
    suspend fun signUp(signUpData: SignUpDataEntity)

    /**
     * Get account entity from database.
     */
    suspend fun getAccount(): AccountDataEntity

    /**
     * Update account info and get updated account entity.
     */
    suspend fun updateAccount(username: String, email: String): AccountDataEntity

    suspend fun logout()
}