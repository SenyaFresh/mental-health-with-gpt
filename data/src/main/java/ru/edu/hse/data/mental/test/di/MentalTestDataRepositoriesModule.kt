package ru.edu.hse.data.mental.test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.data.mental.test.repository.MentalTestDataRepository
import ru.edu.hse.data.mental.test.repository.RealMentalTestDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MentalTestDataRepositoriesModule {

    @Binds
    @Singleton
    fun bindDepressionTestDataRepository(
        depressionTestDataRepository: RealMentalTestDataRepository
    ): MentalTestDataRepository

}