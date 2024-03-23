package ru.edu.hse.data.gpt.repository

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer

interface ChatGPTDataRepository {

    /**
     * Get container of chatGPT response.
     */
    fun getResponse(message: String): Flow<ResultContainer<String>>

}