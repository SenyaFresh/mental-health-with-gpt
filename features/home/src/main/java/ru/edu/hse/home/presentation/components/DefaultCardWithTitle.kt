package ru.edu.hse.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import ru.edu.hse.themes.DefaultCard
import ru.edu.hse.themes.DefaultText
import ru.edu.hse.themes.OutlineColor
import ru.edu.hse.themes.OutlineThickness

@Composable
fun DefaultCardWithTitle(title: String, modifier: Modifier = Modifier, color: Color = OutlineColor, content: @Composable ColumnScope.() -> Unit) {
    DefaultCard(modifier = modifier) {
        Column {
            DefaultText(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
            HorizontalDivider(thickness = OutlineThickness, color = OutlineColor)
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultCardWithTitlePreview() {
    DefaultCardWithTitle(title = "Title", modifier = Modifier.padding(12.dp)) {
        DefaultText(text = "Content", modifier = Modifier
            .padding(12.dp)
            .size(120.dp) )
    }
}