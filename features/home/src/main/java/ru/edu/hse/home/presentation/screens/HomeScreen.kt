package ru.edu.hse.home.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.R
import ru.edu.hse.home.domain.entities.DepressionTest
import ru.edu.hse.home.domain.entities.HealthData
import ru.edu.hse.home.presentation.components.DefaultCardWithTitle
import ru.edu.hse.home.presentation.components.HealthDataItem
import ru.edu.hse.home.presentation.components.ResultContainerWithPermissionsComposable
import ru.edu.hse.home.presentation.components.TestDataItem
import ru.edu.hse.home.presentation.utils.depressionLevelCalculator
import ru.edu.hse.presentation.ResultContainerComposable
import ru.edu.hse.themes.DefaultButton
import ru.edu.hse.themes.DefaultText
import ru.edu.hse.themes.SecondaryColor

@Composable
fun HomeScreen(
    healthContainer: ResultContainer<HealthData>,
    healthOnLoad: () -> Unit,
    missionContainer: ResultContainer<String>,
    missionOnLoad: () -> Unit,
    depressionStatsContainer: ResultContainer<Int>,
    depressionStatsOnLoad: () -> Unit,
    depressionTestContainer: ResultContainer<DepressionTest>,
    depressionTestOnLoad: () -> Unit,
    onPermissionsLaunch: () -> Unit,
    onUpdateDepressionPoints: (Int) -> Unit
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
                    onTryAgain = healthOnLoad,
                    onPermissionsLaunch = onPermissionsLaunch
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
                                    painterResource(id = R.drawable.ic_blood_pressure),
                                    contentDescription = null
                                )
                            },
                            value = (healthContainer.unwrap().bloodPressureSystolicAvg?.toString()
                                ?: "-") +
                                    "/" + (healthContainer.unwrap().bloodPressureDiastolicAvg?.toString()
                                ?: "-"),
                            suffix = "мм рт. ст."
                        )
                    }
                }
            }
        }



        DefaultCardWithTitle(title = "Ежедневная миссия", modifier = Modifier.padding(10.dp)) {
            ResultContainerComposable(container = missionContainer, onTryAgain = missionOnLoad) {
                DefaultText(
                    text = missionContainer.unwrap(),
                    modifier = Modifier.padding(12.dp),
                    fontSize = 16.sp
                )
            }
        }



        DefaultCardWithTitle(title = "Уровень депрессии", modifier = Modifier.padding(10.dp)) {

            var showTest by rememberSaveable {
                mutableStateOf(false)
            }

            var categoryIndex by rememberSaveable {
                mutableIntStateOf(0)
            }

            var depressionPoints by rememberSaveable {
                mutableIntStateOf(0)
            }

            if (!showTest) {
                ResultContainerComposable(
                    container = depressionStatsContainer,
                    onTryAgain = depressionStatsOnLoad
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .height(100.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        val depressionText: String
                        val depressionTestButtonText: String

                        if (depressionStatsContainer.unwrap() == -1) {
                            depressionText = "Давайте пройдем тест и узнаем ваш уровень депрессии."
                            depressionTestButtonText = "Пройти тест"
                        } else {
                            depressionText = "Уровень депрессии: ${
                                depressionLevelCalculator(depressionStatsContainer.unwrap())
                            }."
                            depressionTestButtonText = "Пройти тест еще раз"
                        }

                        DefaultText(text = depressionText, fontSize = 16.sp)

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DefaultButton(
                                text = depressionTestButtonText,
                                onClick = {
                                    categoryIndex = 0
                                    depressionPoints = 0
                                    showTest = true
                                },
                                containerColor = SecondaryColor
                            )
                        }
                    }
                }
            } else {
                ResultContainerComposable(
                    container = depressionTestContainer,
                    onTryAgain = depressionTestOnLoad
                ) {
                    val category = depressionTestContainer.unwrap().categories[categoryIndex]

                    val categoryItems = listOf(
                        category.firstStatement,
                        category.secondStatement,
                        category.thirdStatement,
                        category.fourthStatement
                    )

                    TestDataItem(items = categoryItems, onClick = {
                        depressionPoints += it
                        if (categoryIndex == depressionTestContainer.unwrap().categories.lastIndex) {
                            onUpdateDepressionPoints(depressionPoints)
                            showTest = false
                        } else {
                            categoryIndex++
                        }
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
        healthContainer = ResultContainer.Success(HealthData(4122, 75, 120, 100)),
        healthOnLoad = {},
        missionContainer = ResultContainer.Success("Совершите одно доброе дело без ожидания благодарности. Например, уступите место в транспорте или улыбнитесь незнакомцу."),
        missionOnLoad = {},
        depressionStatsContainer = ResultContainer.Success(-1),
        depressionStatsOnLoad = {},
        depressionTestContainer = ResultContainer.Success(DepressionTest(listOf())),
        depressionTestOnLoad = {},
        onPermissionsLaunch = {},
        onUpdateDepressionPoints = {}
    )
}