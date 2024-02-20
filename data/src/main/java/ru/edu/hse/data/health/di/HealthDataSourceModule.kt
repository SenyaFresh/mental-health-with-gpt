package ru.edu.hse.data.health.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.data.health.sources.HealthConnectHealthDataSource
import ru.edu.hse.data.health.sources.HealthDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HealthDataSourceModule {

    @Binds
    @Singleton
    fun bindHealthDataSource(
        healthDataSource: HealthConnectHealthDataSource
    ): HealthDataSource

}