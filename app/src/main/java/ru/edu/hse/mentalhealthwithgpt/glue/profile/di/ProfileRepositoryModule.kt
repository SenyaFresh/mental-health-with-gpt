package ru.edu.hse.mentalhealthwithgpt.glue.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.mentalhealthwithgpt.glue.profile.repositories.AdapterProfileRepository
import ru.edu.hse.profile.domain.repositories.ProfileRepository


@Module
@InstallIn(SingletonComponent::class)
interface ProfileRepositoryModule {

    @Binds
    fun bindProfileRepository(
        profileRepository: AdapterProfileRepository
    ): ProfileRepository
}