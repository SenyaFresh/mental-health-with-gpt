package ru.edu.hse.themes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal
) = Text(
    text,
    modifier = modifier,
    color = color,
    fontFamily = Nunito,
    fontWeight = fontWeight,
    fontStyle = fontStyle
)

@Preview(showBackground = true)
@Composable
fun DefaultTextPreview() {
    DefaultText(text = "Preview", modifier = Modifier.padding(20.dp))
}
