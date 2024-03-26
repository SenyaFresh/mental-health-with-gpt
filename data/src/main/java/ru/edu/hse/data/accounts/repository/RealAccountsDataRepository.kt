package ru.edu.hse.data.accounts.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.common.flow.LazyFlowLoaderFactory
import ru.edu.hse.data.accounts.entities.AccountDataEntity
import ru.edu.hse.data.accounts.entities.SignUpDataEntity
import ru.edu.hse.data.accounts.sources.AccountsDataSource
import javax.inject.Inject

class RealAccountsDataRepository @Inject constructor(
    private val accountsDataSource: AccountsDataSource,
    scope: CoroutineScope,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : AccountsDataRepository {

    private val accountLazyFlowLoader = lazyFlowLoaderFactory.create {
        accountsDataSource.getAccount()
    }

    init {
        scope.launch {
            accountsDataSource.listenAuthState().collect {
                if (it == null) {
                    accountLazyFlowLoader.update(ResultContainer.Error(AuthenticationException()))
                } else {
                    accountLazyFlowLoader.newAsyncLoad(silently = true)
                }
            }
        }
    }

    override suspend fun signIn(email: String, username: String) {
        accountsDataSource.signIn(email, username)
    }

    override suspend fun singUp(signUpDataEntity: SignUpDataEntity) {
        accountsDataSource.signUp(signUpDataEntity)
    }

    override suspend fun updateAccountUsernameAndEmail(username: String, email: String) {
        val updatedAccount = accountsDataSource.updateAccount(username = username, email = email)
        accountLazyFlowLoader.update(ResultContainer.Done(updatedAccount))
    }

    override suspend fun logout() {
        accountsDataSource.logout()
    }

    override fun reload() {
        accountLazyFlowLoader.newAsyncLoad()
    }

    override fun getAccount(): Flow<ResultContainer<AccountDataEntity>> {
        return accountLazyFlowLoader.listen()
    }
}