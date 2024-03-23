package ru.edu.hse.data.gpt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.data.gpt.repository.ChatGPTDataRepository
import ru.edu.hse.data.gpt.repository.RealChatGPTDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ChatGPTDataRepositoriesModule {

    @Binds
    @Singleton
    fun bindChatGPTDataRepository(
        chatGPTDataRepository: RealChatGPTDataRepository
    ): ChatGPTDataRepository

}