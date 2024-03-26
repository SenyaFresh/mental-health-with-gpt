package ru.edu.hse.mentalhealthwithgpt.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import ru.edu.hse.themes.PrimaryColor
import ru.edu.hse.themes.SecondaryColor

private val ColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor
)

@Composable
fun MentalHealthWithGPTTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.White.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}