package ru.edu.hse.data.accounts.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.data.accounts.repository.AccountsDataRepository
import ru.edu.hse.data.accounts.repository.RealAccountsDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountRepositoriesModule {

    @Binds
    @Singleton
    fun bindAccountsDataRepository(
        accountsDataRepository: RealAccountsDataRepository
    ): AccountsDataRepository

}