package ru.edu.hse.data.accounts.sources

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.data.accounts.entities.AccountDataEntity
import ru.edu.hse.data.accounts.entities.SignUpDataEntity

interface AccountsDataSource {

    fun listenAuthState() : Flow<String?>

    suspend fun signIn(email: String, password: String)

    suspend fun signUp(signUpData: SignUpDataEntity)

    suspend fun getAccount(): AccountDataEntity

    suspend fun updateAccount(username: String, email: String): AccountDataEntity

    suspend fun logout()
}