package ru.edu.hse.mentalhealthwithgpt.glue.sign_up.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.mentalhealthwithgpt.glue.sign_up.repositories.AdapterSignUpRepository
import ru.edu.hse.sign_up.domain.repositories.SignUpRepository


@Module
@InstallIn(SingletonComponent::class)
interface SignUpRepositoryModule {

    @Binds
    fun bindSignUpRepository(
        signUpRepository: AdapterSignUpRepository
    ): SignUpRepository
}