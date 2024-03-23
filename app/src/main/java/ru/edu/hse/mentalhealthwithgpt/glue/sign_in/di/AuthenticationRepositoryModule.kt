package ru.edu.hse.mentalhealthwithgpt.glue.sign_in.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.mentalhealthwithgpt.glue.sign_in.repositories.AdapterAuthenticationRepository
import ru.edu.hse.sign_in.domain.repositories.AuthenticationRepository

@Module
@InstallIn(SingletonComponent::class)
interface AuthenticationRepositoryModule {

    @Binds
    fun bindAuthenticationRepository(
        authenticationRepository: AdapterAuthenticationRepository
    ): AuthenticationRepository
}