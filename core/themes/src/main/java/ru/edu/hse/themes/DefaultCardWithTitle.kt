package ru.edu.hse.themes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultCardWithTitle(
    title: String,
    modifier: Modifier = Modifier,
    color: Color = OutlineColor,
    content: @Composable ColumnScope.() -> Unit
) {
    DefaultCard(modifier = modifier, color = color) {
        Column {
            DefaultText(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryColor)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
            HorizontalDivider(thickness = OutlineThickness, color = OutlineColor)
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultCardWithTitlePreview() {
    DefaultCardWithTitle(title = "Title", modifier = Modifier.padding(12.dp)) {
        DefaultText(
            text = "Content",
            modifier = Modifier
                .padding(12.dp)
                .size(120.dp)
        )
    }
}