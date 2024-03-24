package ru.edu.hse.sign_up.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.edu.hse.sign_up.presentation.events.SignUpEvent
import ru.edu.hse.sign_up.presentation.viewmodels.SignUpViewModel
import ru.edu.hse.themes.DefaultButton
import ru.edu.hse.themes.DefaultTextField
import ru.edu.hse.themes.DefaultTitle
import ru.edu.hse.themes.EmailIcon
import ru.edu.hse.themes.NameIcon
import ru.edu.hse.themes.PasswordIcon

@Composable
fun SignUpScreen(
    state: SignUpViewModel.State,
    onEvent: (SignUpEvent) -> Unit,
    launchMainFlag: Boolean,
    onLaunchMain: () -> Unit,
    onRestartApp: () -> Unit
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }

    var username by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    if (launchMainFlag) {
        onLaunchMain()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp, start = 30.dp, end = 30.dp)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DefaultTitle(modifier = Modifier.padding(bottom = 20.dp), text = "Создать аккаунт")

        DefaultTextField(
            modifier = Modifier.padding(bottom = 20.dp),
            value = email,
            onValueChange = {
                email = it
                onEvent(SignUpEvent.DisableEmailError)
            },
            icon = { EmailIcon() },
            label = "Email",
            hint = "Введите email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            error = state.emailError
        )

        DefaultTextField(
            modifier = Modifier.padding(bottom = 20.dp),
            value = username,
            onValueChange = {
                username = it
                onEvent(SignUpEvent.DisableUsernameError)
            },
            icon = { NameIcon() },
            label = "Имя пользователя",
            hint = "Введите ваше имя",
            error = state.usernameError
        )

        DefaultTextField(
            modifier = Modifier.padding(bottom = 20.dp),
            value = password,
            onValueChange = {
                password = it
                onEvent(SignUpEvent.DisablePasswordError)
            },
            icon = { PasswordIcon() },
            label = "Пароль",
            hint = "Придумайте пароль",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            error = state.passwordError
        )

        DefaultButton(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .height(45.dp),
            text = "Подтвердить",
            enabled = state.enableButtons,
            onClick = { onEvent(SignUpEvent.SignUp(email, username, password)) }
        )

        if (state.showProgressBar) {
            CircularProgressIndicator(
                color = Color.Black
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        state = SignUpViewModel.State(false, false, false, false),
        onEvent = { },
        launchMainFlag = false,
        onLaunchMain = { },
        onRestartApp = { }
    )
}