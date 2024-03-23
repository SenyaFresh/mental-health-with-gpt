package ru.edu.hse.assistant.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.assistant.domain.repositories.ChatGPTRepository
import ru.edu.hse.common.ResultContainer
import javax.inject.Inject

class GetResponseUseCase @Inject constructor(
    private val chatGPTRepository: ChatGPTRepository
) {

    suspend fun getResponse(message: String): Flow<ResultContainer<String>> {
        return chatGPTRepository.getResponse(message)
    }

}