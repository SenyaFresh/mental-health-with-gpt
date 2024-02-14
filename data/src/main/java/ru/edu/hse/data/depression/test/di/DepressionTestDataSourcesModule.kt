package ru.edu.hse.data.depression.test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.data.depression.test.sources.DepressionTestDataSource
import ru.edu.hse.data.depression.test.sources.FirebaseDepressionTestDataSource
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DepressionTestDataSourcesModule {

    @Binds
    @Singleton
    fun bindDepressionTestSource(
        depressionTestDataSource: FirebaseDepressionTestDataSource
    ) : DepressionTestDataSource
}