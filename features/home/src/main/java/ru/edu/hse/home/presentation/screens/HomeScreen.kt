package ru.edu.hse.home.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.R
import ru.edu.hse.home.domain.entities.DepressionTest
import ru.edu.hse.home.domain.entities.HealthData
import ru.edu.hse.home.presentation.components.DefaultCardWithTitle
import ru.edu.hse.home.presentation.components.HealthDataItem
import ru.edu.hse.presentation.ResultContainerComposable
import ru.edu.hse.themes.DefaultText

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
    permissions: Set<String>,
    onPermissionsLaunch: (Set<String>) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DefaultCardWithTitle(title = "Моё здоровье", modifier = Modifier.padding(8.dp)) {
            Box(modifier = Modifier.height(400.dp)) {
                ResultContainerComposable(container = healthContainer, onTryAgain = healthOnLoad) {
                    Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .fillMaxSize()) {
                        HealthDataItem(
                            icon = {
                                Image(
                                    painterResource(id = R.drawable.ic_footprints),
                                    contentDescription = null
                                )
                            },
                            title = "Пройденные шаги",
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
                            title = "Пульс",
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
                            title = "Артериальное давление",
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
                DefaultText(text = missionContainer.unwrap(), modifier = Modifier.padding(12.dp))
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
        depressionStatsContainer = ResultContainer.Success(12),
        depressionStatsOnLoad = {},
        depressionTestContainer = ResultContainer.Success(DepressionTest(listOf())),
        depressionTestOnLoad = {},
        permissions = setOf(),
        onPermissionsLaunch = {})
}