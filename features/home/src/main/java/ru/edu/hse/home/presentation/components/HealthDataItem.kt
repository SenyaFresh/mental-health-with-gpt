package ru.edu.hse.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.edu.hse.themes.DefaultText


@Composable
fun HealthDataItem(
    icon: @Composable () -> Unit,
    title: String,
    value: String,
    suffix: String,
    modifier: Modifier = Modifier
) {

    DefaultCardWithTitle(
        title = title,
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(110.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(110.dp)
            ) {
                icon()
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(bottom = 30.dp, end = 20.dp)
            ) {
                DefaultText(text = value, fontSize = 60.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.offset(0.dp, 16.dp))
                DefaultText(text = suffix, fontSize = 20.sp, fontWeight = FontWeight.Light, fontStyle = FontStyle.Italic)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HealthDataItemPreviewPressure() {
    HealthDataItem(
        icon = { Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null, modifier = Modifier.fillMaxSize()) },
        title = "Давление",
        value = "150/100",
        suffix = "",
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun HealthDataItemPreviewPulse() {
    HealthDataItem(
        icon = { Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = null, modifier = Modifier.fillMaxSize()) },
        title = "Пульс",
        value = "120",
        suffix = "уд/мин",
        modifier = Modifier.padding(vertical = 16.dp)
    )
}