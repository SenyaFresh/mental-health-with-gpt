package ru.edu.hse.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    borderColor: Color = OutlineColor,
    containerColor: Color = PrimaryColor,
    enabled: Boolean = true
) = OutlinedButton(
    modifier = modifier,
    border = BorderStroke(
        width = OutlineThickness,
        color = borderColor
    ),
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(
        containerColor = containerColor,
    ),
    elevation = ButtonDefaults.buttonElevation(0.dp),
    shape = RoundedCornerShape(CornerSize),
    enabled = enabled
) {
    DefaultText(text, fontWeight = FontWeight.SemiBold)
}

@Preview(showBackground = true)
@Composable
fun DefaultButtonPreview() {
    DefaultButton(modifier = Modifier.padding(20.dp),text = "Preview", onClick = {  })
}

