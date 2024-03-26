package ru.edu.hse.assistant.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.presentation.ResultContainerComposable
import ru.edu.hse.components.DefaultButton
import ru.edu.hse.components.DefaultCard
import ru.edu.hse.components.DefaultText
import ru.edu.hse.components.DefaultTextField

@Composable
fun AssistantScreen(
    responseContainer: ResultContainer<String>,
    onGetResponse: (String) -> Unit,
    onRestartApp: () -> Unit
) {

    var message by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        DefaultTextField(
            value = message,
            onValueChange = { message = it },
            icon = {
                Icon(
                    painterResource(id = ru.edu.hse.assistant.R.drawable.ic_question),
                    contentDescription = "email icon"
                )
            },
            label = "Задайте вопрос ИИ-ассистенту",
            hint = "Введите вопрос..."
        )

        Spacer(modifier = Modifier.height(12.dp))

        DefaultButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            text = "Отправить",
            onClick = { onGetResponse(message) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        DefaultCard(modifier = Modifier.fillMaxSize()) {
            DefaultText(
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp),
                text = "Ответ: ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            ResultContainerComposable(
                container = responseContainer,
                onTryAgain = { onGetResponse(message) },
                onRestartApp = { onRestartApp() }) {
                DefaultText(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    text = responseContainer.unwrap(),
                    fontSize = 16.sp
                )
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun AssistantScreenPreview() {
    AssistantScreen(
        responseContainer = ResultContainer.Done("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
        onGetResponse = { },
        onRestartApp = { }
    )
}