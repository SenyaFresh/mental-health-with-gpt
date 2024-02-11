package ru.edu.hse.data

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.data.accounts.entities.AccountDataEntity
import ru.edu.hse.data.accounts.entities.SignUpDataEntity

interface AccountsDataRepository {

    suspend fun signIn(email: String, username: String)

    suspend fun singUp(signUpDataEntity: SignUpDataEntity)

    suspend fun updateAccount(username: String, email: String)

    suspend fun logout()

    fun reload()

    fun getAccount(): Flow<ResultContainer<AccountDataEntity>>

}