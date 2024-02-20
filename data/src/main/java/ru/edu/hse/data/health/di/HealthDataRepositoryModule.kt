package ru.edu.hse.data.health.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.data.health.repository.HealthDataRepository
import ru.edu.hse.data.health.repository.RealHealthDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HealthDataRepositoryModule {
    @Binds
    @Singleton
    fun bindHealthDataRepository(
        healthDataRepository: RealHealthDataRepository
    ): HealthDataRepository
}