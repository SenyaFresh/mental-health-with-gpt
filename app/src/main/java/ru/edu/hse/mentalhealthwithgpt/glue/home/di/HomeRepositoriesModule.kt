package ru.edu.hse.mentalhealthwithgpt.glue.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.home.domain.repositories.EverydayMissionsRepository
import ru.edu.hse.home.domain.repositories.HealthRepository
import ru.edu.hse.home.domain.repositories.MentalTestRepository
import ru.edu.hse.mentalhealthwithgpt.glue.home.repositories.AdapterEverydayMissionsRepository
import ru.edu.hse.mentalhealthwithgpt.glue.home.repositories.AdapterHealthRepository
import ru.edu.hse.mentalhealthwithgpt.glue.home.repositories.AdapterMentalTestRepository

@Module
@InstallIn(SingletonComponent::class)
interface HomeRepositoriesModule {

    @Binds
    fun bindEverydayMissionsRepository(
        everydayMissionsRepository: AdapterEverydayMissionsRepository
    ): EverydayMissionsRepository

    @Binds
    fun bindHealthRepository(
        healthMissionsRepository: AdapterHealthRepository
    ): HealthRepository

    @Binds
    fun bindMentalTestRepository(
        mentalTestRepository: AdapterMentalTestRepository
    ): MentalTestRepository

}