package ru.edu.hse.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCard(
    modifier: Modifier = Modifier, color: Color = OutlineColor, outlineThickness: Dp = OutlineThickness, content: @Composable ColumnScope.() -> Unit
) = Card(
    modifier = modifier,
    border = BorderStroke(
        width = outlineThickness,
        color = color
    ),
    elevation = CardDefaults.elevatedCardElevation(0.dp),
    shape = RoundedCornerShape(CornerSize),
    colors = CardDefaults.cardColors(
        containerColor = Color.Transparent
    ),
    content = content
)


@Preview(showBackground = true)
@Composable
fun DefaultCardPreview() {
    DefaultCard(modifier = Modifier.padding(20.dp)) {
        DefaultText(modifier = Modifier.size(200.dp).padding(10.dp), text = "Preview")
    }
}