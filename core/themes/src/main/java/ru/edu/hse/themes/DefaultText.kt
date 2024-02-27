package ru.edu.hse.themes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.SemiBold,
    fontStyle: FontStyle = FontStyle.Normal,
    maxLines: Int = 20
) = Text(
    text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontFamily = Nunito,
    fontWeight = fontWeight,
    fontStyle = fontStyle,
    maxLines = maxLines
)

@Preview(showBackground = true)
@Composable
fun DefaultTextPreview() {
    DefaultText(text = "Preview", modifier = Modifier.padding(20.dp))
}
