package ru.edu.hse.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import ru.edu.hse.home.domain.entities.MentalTestQuestionEntity
import ru.edu.hse.mylibrary.R
import ru.edu.hse.components.DefaultButton
import ru.edu.hse.components.DefaultCardWithTitle
import ru.edu.hse.components.DefaultText
import ru.edu.hse.components.DefaultTextField
import ru.edu.hse.components.SecondaryColor

@Composable
fun TestDataItem(
    questionEntity: MentalTestQuestionEntity,
    onClick: (MentalTestQuestionEntity, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 12.dp)
    ) {

        DefaultText(
            modifier = Modifier.padding(6.dp),
            text = questionEntity.question,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        if (questionEntity.questionType == "withOptions") {
            for (i in questionEntity.answers!!.indices) {
                DefaultText(
                    text = "${i + 1}. ${questionEntity.answers[i]}", modifier = Modifier
                        .clickable(onClick = { onClick(questionEntity, questionEntity.answers[i]) })
                        .padding(vertical = 8.dp, horizontal = 6.dp)
                )
            }
            
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
        } else if (questionEntity.questionType == "open") {

            var answer by rememberSaveable {
                mutableStateOf("")
            }

            DefaultTextField(
                modifier = Modifier.padding(4.dp),
                value = answer,
                onValueChange = { answer = it },
                icon = {
                    Icon(
                        painterResource(id = R.drawable.ic_pen),
                        contentDescription = "pen icon"
                    )
                },
                label = "Ответ",
                hint = "Введите ответ..."
            )

            DefaultButton(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
                text = "Дальше",
                containerColor = SecondaryColor,
                onClick = { onClick(questionEntity, answer) }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TestDataItemPreview() {
    DefaultCardWithTitle(title = "Тест", modifier = Modifier.padding(20.dp)) {
        TestDataItem(
            MentalTestQuestionEntity(
                "a",
                listOf("Никогда", "Редко", "Иногда", "Часто", "Постоянно"),
                "Пример очень очень очень очень очень очень очень очень очень очень очень длинного вопроса.",
                "withOptions"
            ),
            { _, _ -> }
        )
    }
}