package ru.edu.hse.data.mental.test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.data.mental.test.source.MentalTestDataSource
import ru.edu.hse.data.mental.test.source.RealMentalTestDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MentalTestDataSourceModule {

    @Binds
    @Singleton
    fun bindMentalTestDataSource(
        mentalTestDataSource: RealMentalTestDataSource
    ): MentalTestDataSource

}