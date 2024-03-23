package ru.edu.hse.mentalhealthwithgpt.glue.assistant.repositories

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.assistant.domain.repositories.ChatGPTRepository
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.data.gpt.repository.RealChatGPTDataRepository
import javax.inject.Inject

class AdapterChatGPTRepository @Inject constructor(
    private val chatGPTDataRepository: RealChatGPTDataRepository
) : ChatGPTRepository {

    override suspend fun getResponse(message: String): Flow<ResultContainer<String>> {
        return chatGPTDataRepository.getResponse(message)
    }
}