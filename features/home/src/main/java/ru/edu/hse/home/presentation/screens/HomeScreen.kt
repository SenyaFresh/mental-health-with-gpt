package ru.edu.hse.home.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.R
import ru.edu.hse.home.domain.entities.EverydayMissionEntity
import ru.edu.hse.home.domain.entities.EverydayMissionsListEntity
import ru.edu.hse.home.domain.entities.HealthData
import ru.edu.hse.home.domain.entities.MentalTestEntity
import ru.edu.hse.home.domain.entities.MentalTestQuestionEntity
import ru.edu.hse.home.presentation.components.EverydayMissionsList
import ru.edu.hse.home.presentation.components.HealthDataItem
import ru.edu.hse.home.presentation.components.ResultContainerWithPermissionsComposable
import ru.edu.hse.home.presentation.components.TestDataItem
import ru.edu.hse.home.presentation.events.HomeEvent
import ru.edu.hse.presentation.ResultContainerComposable
import ru.edu.hse.components.DefaultButton
import ru.edu.hse.components.DefaultCardWithTitle
import ru.edu.hse.components.SecondaryColor
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    healthContainer: ResultContainer<HealthData>,
    missionsContainer: ResultContainer<EverydayMissionsListEntity>,
    mentalTestContainer: ResultContainer<MentalTestEntity>,
    onPermissionsLaunch: () -> Unit,
    onEvent: (HomeEvent) -> Unit,
    onRestartApp: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DefaultCardWithTitle(title = "Моё здоровье", modifier = Modifier.padding(8.dp)) {
            Box(modifier = Modifier.height(300.dp)) {
                ResultContainerWithPermissionsComposable(
                    container = healthContainer,
                    onTryAgain = { onEvent(HomeEvent.HealthOnLoad) },
                    onPermissionsLaunch = onPermissionsLaunch,
                    onRestartApp = onRestartApp
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .fillMaxSize()
                    ) {
                        HealthDataItem(
                            icon = {
                                Image(
                                    painterResource(id = R.drawable.ic_footprints),
                                    contentDescription = null
                                )
                            },
                            value = healthContainer.unwrap().stepsCount?.toString() ?: "-",
                            suffix = "шаг."
                        )

                        HealthDataItem(
                            icon = {
                                Image(
                                    painterResource(id = R.drawable.ic_heart_rate),
                                    contentDescription = null
                                )
                            },
                            value = healthContainer.unwrap().heartRateAvg?.toString() ?: "-",
                            suffix = "уд/мин"
                        )

                        HealthDataItem(
                            icon = {
                                Image(
                                    painterResource(id = R.drawable.ic_sleep),
                                    contentDescription = null
                                )
                            },
                            value = healthContainer.unwrap().sleepMinutes?.toDouble()?.div(60)
                                ?.roundToInt()?.toString() ?: "-",
                            suffix = "ч."
                        )
                    }
                }
            }
        }

        DefaultCardWithTitle(title = "Ежедневные миссии", modifier = Modifier.padding(10.dp)) {
            ResultContainerComposable(
                container = missionsContainer,
                onTryAgain = { onEvent(HomeEvent.EverydayMissionsOnLoad) },
                onRestartApp = onRestartApp
            ) {
                EverydayMissionsList(
                    missionsContainer.unwrap().missionsList,
                    onMissionCompleted = { onEvent(HomeEvent.SetMissionCompletionEvent(it)) })
            }
        }

        DefaultCardWithTitle(
            title = "Тест на ментальное здоровье",
            modifier = Modifier.padding(10.dp)
        ) {
            ResultContainerComposable(
                container = mentalTestContainer,
                onTryAgain = { onEvent(HomeEvent.MentalTestOnLoad) },
                onRestartApp = onRestartApp
            ) {
                var showTest by rememberSaveable {
                    mutableStateOf(false)
                }

                var questionIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                if (!showTest) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(18.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DefaultButton(
                            text = "Пройти тест",
                            onClick = { showTest = true },
                            containerColor = SecondaryColor
                        )
                    }
                } else {
                    val questionEntity =
                        mentalTestContainer.unwrap().mentalTestQuestions[questionIndex]

                    TestDataItem(questionEntity = questionEntity, onClick = { question, answer ->
                        Snapshot.withMutableSnapshot {
                            if (questionIndex == mentalTestContainer.unwrap().mentalTestQuestions.lastIndex) {
                                showTest = false
                                questionIndex = 0
                            } else {
                                questionIndex++
                            }
                        }
                        onEvent(HomeEvent.SetMentalTestAnswerEvent(question, answer))
                    })
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        healthContainer = ResultContainer.Done(HealthData(7000, 70, 360)),
        missionsContainer = ResultContainer.Done(
            EverydayMissionsListEntity(
                listOf(
                    EverydayMissionEntity(text = "Прочитать книгу"),
                    EverydayMissionEntity(text = "Сделать зарядку", completed = true),
                    EverydayMissionEntity(text = "Выпить 2 литра воды")
                )
            )
        ),
        mentalTestContainer = ResultContainer.Done(
            MentalTestEntity(
                "af",
                listOf(
                    MentalTestQuestionEntity(
                        "a",
                        listOf("Никогда", "Редко", "Иногда", "Часто", "Постоянно"),
                        "Пример вопроса",
                        "withOptions"
                    )
                ),
                "a"
            )
        ),
        onPermissionsLaunch = { },
        onEvent = { },
        onRestartApp = { }
    )
}