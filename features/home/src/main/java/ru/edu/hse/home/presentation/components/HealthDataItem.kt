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
import ru.edu.hse.components.DefaultCard
import ru.edu.hse.components.DefaultText


@Composable
fun HealthDataItem(
    icon: @Composable () -> Unit,
    value: String,
    suffix: String,
    modifier: Modifier = Modifier
) {

    DefaultCard(
        modifier = modifier.padding(horizontal = 8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(80.dp)
            ) {
                icon()
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(bottom = 14.dp, end = 20.dp)
            ) {
                DefaultText(
                    text = value,
                    fontSize = if (value.length > 4) 46.sp else 56.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = if (value.length > 4) Modifier.offset(0.dp, 12.dp)
                    else Modifier.offset(0.dp, 4.dp)
                )
                DefaultText(
                    text = suffix,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HealthDataItemPreviewPressure() {
    HealthDataItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        },
        value = "150/100",
        suffix = "мм рт. ст.",
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun HealthDataItemPreviewPulse() {
    HealthDataItem(
        icon = {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        },
        value = "120",
        suffix = "уд/мин",
        modifier = Modifier.padding(vertical = 16.dp)
    )
}