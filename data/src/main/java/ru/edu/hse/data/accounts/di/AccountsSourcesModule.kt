package ru.edu.hse.data.accounts.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.data.accounts.sources.AccountsDataSource
import ru.edu.hse.data.accounts.sources.FirebaseAccountsDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountsSourcesModule {

    @Binds
    @Singleton
    fun bindAccountSource(
        accountsDataSource: FirebaseAccountsDataSource
    ) : AccountsDataSource
}