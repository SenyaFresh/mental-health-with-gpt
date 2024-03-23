package ru.edu.hse.assistant.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer

interface ChatGPTRepository {

    /**
     * Get response from chatGPT assistant.
     */
    suspend fun getResponse(message: String): Flow<ResultContainer<String>>

}