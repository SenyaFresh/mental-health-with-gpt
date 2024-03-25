package ru.edu.hse.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.edu.hse.home.domain.entities.EverydayMissionEntity
import ru.edu.hse.themes.DefaultText
import ru.edu.hse.themes.PrimaryColor

@Composable
fun EverydayMissionsList(
    dailyMissions: List<EverydayMissionEntity>,
    onMissionCompleted: (EverydayMissionEntity) -> Unit
) {

    Column(modifier = Modifier.padding(16.dp)) {
        for (mission in dailyMissions) {
            EverydayMissionItem(mission, onMissionCompleted)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun EverydayMissionItem(
    mission: EverydayMissionEntity,
    onMissionCompleted: (EverydayMissionEntity) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var onMissionPressed by rememberSaveable {
            mutableStateOf(mission.completed)
        }

        Checkbox(
            checked = onMissionPressed,
            onCheckedChange = {
                onMissionPressed = it
                onMissionCompleted(mission.copy(completed = it))
            },
            colors = CheckboxDefaults.colors(checkedColor = PrimaryColor)
        )
        Spacer(modifier = Modifier.width(8.dp))
        DefaultText(
            text = mission.text,
            color = if (onMissionPressed) Color.LightGray else Color.Black,
            modifier = Modifier
                .clickable {
                    onMissionPressed = !onMissionPressed
                    onMissionCompleted(mission.copy(completed = onMissionPressed))
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEverydayMissionsList() {
    val previewMissions = listOf(
        EverydayMissionEntity(text = "Прочитать книгу"),
        EverydayMissionEntity(text = "Сделать зарядку", completed = true),
        EverydayMissionEntity(text = "Выпить 2 литра воды")
    )
    EverydayMissionsList(
        dailyMissions = previewMissions,
        onMissionCompleted = {}
    )
}

