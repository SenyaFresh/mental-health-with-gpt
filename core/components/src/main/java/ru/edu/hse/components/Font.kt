package ru.edu.hse.components

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import ru.edu.hse.mylibrary.R

val Nunito = FontFamily(
    fonts = listOf(
        Font(R.font.nunito_black, FontWeight.Black, FontStyle.Normal),
        Font(R.font.nunito_blackitalic, FontWeight.Black, FontStyle.Italic),
        Font(R.font.nunito_bold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.nunito_bolditalic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.nunito_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
        Font(R.font.nunito_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
        Font(R.font.nunito_extralight, FontWeight.ExtraLight, FontStyle.Normal),
        Font(R.font.nunito_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
        Font(R.font.nunito_regular, FontWeight.Normal, FontStyle.Normal),
        Font(R.font.nunito_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.nunito_light, FontWeight.Light, FontStyle.Normal),
        Font(R.font.nunito_lightitalic, FontWeight.Light, FontStyle.Italic),
        Font(R.font.nunito_medium, FontWeight.Medium, FontStyle.Normal),
        Font(R.font.nunito_mediumitalic, FontWeight.Medium, FontStyle.Italic),
        Font(R.font.nunito_semibold, FontWeight.SemiBold, FontStyle.Normal),
        Font(R.font.nunito_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    )
)