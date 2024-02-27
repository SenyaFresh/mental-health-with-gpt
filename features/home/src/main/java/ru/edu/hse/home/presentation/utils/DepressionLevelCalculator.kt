package ru.edu.hse.home.presentation.utils

fun depressionLevelCalculator(points: Int) : String {
    return if (points < 14) {
        "признаки депрессии отсутствуют"
    } else if (points < 20) {
        "легкая депрессия"
    } else if (points < 28) {
        "умеренная депрессия"
    } else {
        "тяжелая депрессия"
    }
}