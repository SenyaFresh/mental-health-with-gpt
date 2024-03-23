package ru.edu.hse.data.gpt.sources

interface ChatGPTDataSource {

    /**
     * Get response from chatGPT by given message.
     */
    suspend fun getResponse(message: String): String

}