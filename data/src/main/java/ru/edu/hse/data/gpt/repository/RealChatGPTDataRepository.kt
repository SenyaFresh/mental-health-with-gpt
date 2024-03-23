package ru.edu.hse.data.gpt.repository

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.common.flow.LazyFlowLoaderFactory
import ru.edu.hse.data.gpt.sources.ChatGPTDataSource
import javax.inject.Inject

class RealChatGPTDataRepository @Inject constructor(
    private val chatGPTDataSource: ChatGPTDataSource,
    private val lazyFlowLoaderFactory: LazyFlowLoaderFactory
): ChatGPTDataRepository {


    override fun getResponse(message: String): Flow<ResultContainer<String>> {
        val responseFlowLoader = lazyFlowLoaderFactory.create {
            chatGPTDataSource.getResponse(message)
        }

        return responseFlowLoader.listen()
    }
}