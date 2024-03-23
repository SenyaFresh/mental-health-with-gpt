package ru.edu.hse.data.gpt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.data.gpt.sources.ChatGPTDataSource
import ru.edu.hse.data.gpt.sources.OpenAIChatGPTDataSource
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface ChatGPTDataSourcesModule {

    @Binds
    @Singleton
    fun bindChatGPTDataSource(
        chatGPTDataSource: OpenAIChatGPTDataSource
    ) : ChatGPTDataSource
}