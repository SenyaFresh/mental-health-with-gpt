package ru.edu.hse.mentalhealthwithgpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.edu.hse.mentalhealthwithgpt.ui.theme.MentalHealthWithGPTTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentalHealthWithGPTTheme {

            }
        }
    }
}
