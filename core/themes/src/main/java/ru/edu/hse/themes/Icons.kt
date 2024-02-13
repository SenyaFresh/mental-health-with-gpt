package ru.edu.hse.themes

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import ru.edu.hse.mylibrary.R



@Composable
fun EmailIcon() = Icon(painterResource(id = R.drawable.ic_email), contentDescription = "email icon")

@Composable
fun NameIcon() = Icon(painterResource(id = R.drawable.ic_pen), contentDescription = "name icon")

@Composable
fun PasswordIcon() = Icon(painterResource(id = R.drawable.ic_password), contentDescription = "password icon")