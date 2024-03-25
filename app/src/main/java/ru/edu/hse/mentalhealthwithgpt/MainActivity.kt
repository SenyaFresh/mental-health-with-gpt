package ru.edu.hse.mentalhealthwithgpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.edu.hse.mentalhealthwithgpt.navigation.BaseNavigation
import ru.edu.hse.mentalhealthwithgpt.ui.theme.MentalHealthWithGPTTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentalHealthWithGPTTheme {
                BaseNavigation()
            }
        }
    }
}
