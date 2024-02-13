package ru.edu.hse.themes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultTextField(
    value: String,
    onValueChange: (String) -> Unit,
    icon: @Composable () -> Unit,
    label: String,
    hint: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    error: Boolean = false
) {
    val onErrorColor = if (error) {
        Color.Red
    } else {
        Color.Black
    }

    DefaultCard(
        modifier = modifier,
        color = onErrorColor
    ) {
        Column {
            Row(
                modifier = Modifier
                    .height(35.dp)
                    .fillMaxWidth()
                    .background(LightBackgroundColor)
                    .padding(start = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(20.dp)) {icon()}
                DefaultText(
                    label,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                    fontWeight = FontWeight.Medium,
                )
            }
            HorizontalDivider(thickness = 1.dp, color = onErrorColor)
            OutlinedTextField(
                modifier = Modifier
                    .height(60.dp)
                    .padding(horizontal = 3.dp),
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Transparent
                ),
                placeholder = { DefaultText(modifier = Modifier.offset(y = (-3).dp),text = hint, color = LightTextColor) },
                keyboardOptions = keyboardOptions,
                textStyle = TextStyle(fontSize = 16.sp, fontFamily = Nunito, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultTextFieldPreview() {
    DefaultTextField(
        modifier = Modifier.padding(30.dp),
        value = "test@gmail.com",
        onValueChange = { },
        icon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = null) },
        label = "Email",
        hint = "Preview3"
    )
}