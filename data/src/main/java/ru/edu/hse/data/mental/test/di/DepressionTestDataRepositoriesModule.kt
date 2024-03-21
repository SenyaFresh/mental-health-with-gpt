package ru.edu.hse.data.mental.test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.data.mental.test.repository.DepressionTestDataRepository
import ru.edu.hse.data.mental.test.repository.RealDepressionTestDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DepressionTestDataRepositoriesModule {

    @Binds
    @Singleton
    fun bindDepressionTestDataRepository(
        depressionTestDataRepository: RealDepressionTestDataRepository
    ): DepressionTestDataRepository

}