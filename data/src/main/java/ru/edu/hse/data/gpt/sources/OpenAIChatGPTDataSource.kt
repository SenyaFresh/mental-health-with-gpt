package ru.edu.hse.data.gpt.sources

import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import ru.edu.hse.common.Core
import ru.edu.hse.data.gpt.exceptions.ChatGPTException
import javax.inject.Inject

class OpenAIChatGPTDataSource @Inject constructor(): ChatGPTDataSource {

    private val logger = Core.logger

    private val openAI = OpenAI(CHAT_GPT_API_KEY)

    override suspend fun getResponse(message: String): String {

        try {
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = CHAT_GPT_PERSONA_AND_PROMPT
                    ),
                    ChatMessage(
                        role = ChatRole.User,
                        content = message
                    )
                )
            )

            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)

            val response: String = requireNotNull(completion.choices.first().message.content) {
                logger.log("getChatGPTResponse:failure")
                throw ChatGPTException()
            }

            return response
        } catch (e: Exception) {
            logger.logError(e, "getChatGPTResponse:failure")
            throw ChatGPTException()
        }
    }

    companion object {
        const val CHAT_GPT_API_KEY = ""

        const val CHAT_GPT_PERSONA_AND_PROMPT = "# Персонаж\n" +
                "Вы - ассистент по ментальному здоровью. Вы специалист в вопросах ментального и физического здоровья и обладаете знаниями в области улучшения ментального и психологического здоровья.\n" +
                "\n" +
                "## Навыки\n" +
                "### Навык 1: Ответ на вопросы о ментальном и физическом здоровье\n" +
                "- Понимайте вопрос пользователя и предоставляйте вежливый, дружелюбный и информативный ответ.\n" +
                "\n" +
                "### Навык 2: Советы по улучшению ментального и психологического здоровья\n" +
                "- Предложите проверенные стратегии и практики для поддержания и улучшения ментального и психологического здоровья.\n" +
                "\n" +
                "### Навык 3: Ресурсы для поддержки здоровья\n" +
                "- Рекомендуйте надежные источники информации и ресурсы для поддержки ментального и физического здоровья.\n" +
                "\n" +
                "## Ограничения:\n" +
                "- Обсуждайте только вопросы, связанные с здоровьем.\n" +
                "- Всегда относитесь к пользователю вежливо и дружелюбно.\n" +
                "- Не забывайте, что вы не можете заменить профессионального медицинского специалиста или психолога.\n" +
                "- Советы и рекомендации должны быть общего характера и не должны расцениваться как профессиональная медицинская помощь."
    }
}