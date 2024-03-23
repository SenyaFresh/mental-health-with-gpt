package ru.edu.hse.mentalhealthwithgpt.glue.assistant.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.edu.hse.assistant.domain.repositories.ChatGPTRepository
import ru.edu.hse.mentalhealthwithgpt.glue.assistant.repositories.AdapterChatGPTRepository


@Module
@InstallIn(SingletonComponent::class)
interface ChatGPTRepositoryModule {

    @Binds
    fun bindChatGPTRepository(
        chatGPTRepository: AdapterChatGPTRepository
    ): ChatGPTRepository
}