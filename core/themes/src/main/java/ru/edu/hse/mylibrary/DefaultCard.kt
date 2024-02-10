package ru.edu.hse.mylibrary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCard(
    modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) = Card(
    modifier = modifier,
    border = BorderStroke(
        width = 2.dp, color = OutlineColor
    ),
    elevation = CardDefaults.elevatedCardElevation(0.dp),
    shape = RoundedCornerShape(10.dp),
    content = content
)
