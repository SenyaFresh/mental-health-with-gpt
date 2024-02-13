package ru.edu.hse.sign_in.presentation.screens

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
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.presentation.ResultContainerComposable
import ru.edu.hse.sign_in.presentation.events.SignInEvent
import ru.edu.hse.sign_in.presentation.viewmodels.SignInViewModel
import ru.edu.hse.themes.DefaultButton
import ru.edu.hse.themes.DefaultTextField
import ru.edu.hse.themes.EmailIcon
import ru.edu.hse.themes.PasswordIcon
import ru.edu.hse.themes.SecondaryColor


@Composable
fun SignInScreen(
    container: ResultContainer<SignInViewModel.State>,
    onTryAgain: () -> Unit,
    onEvent: (SignInEvent) -> Unit
) {

    ResultContainerComposable(container = container, onTryAgain = onTryAgain) {
        var email by rememberSaveable {
            mutableStateOf("")
        }

        var password by rememberSaveable {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp, start = 30.dp, end = 30.dp)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultTextField(
                modifier = Modifier.padding(bottom = 20.dp),
                value = email,
                onValueChange = {
                    email = it
                    onEvent(SignInEvent.DisableEmailError)
                },
                icon = { EmailIcon() },
                label = "Email",
                hint = "Введите email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                error = container.unwrap().emailError
            )

            DefaultTextField(
                modifier = Modifier.padding(bottom = 20.dp),
                value = password,
                onValueChange = {
                    password = it
                    onEvent(SignInEvent.DisablePasswordError)
                },
                icon = { PasswordIcon() },
                label = "Пароль",
                hint = "Введите пароль",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                error = container.unwrap().passwordError
            )

            DefaultButton(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .height(45.dp)
                    .fillMaxWidth(),
                text = "Войти",
                enabled = container.unwrap().enableButtons,
                onClick = { onEvent(SignInEvent.SignIn(email, password)) }
            )

            DefaultButton(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .height(45.dp),
                text = "Зарегистрироваться",
                containerColor = SecondaryColor,
                enabled = container.unwrap().enableButtons,
                onClick = { onEvent(SignInEvent.LaunchSignUp) }
            )

            if (container.unwrap().showProgressBar) {
                CircularProgressIndicator(
                    color = Color.Black
                )
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        container = ResultContainer.Success(SignInViewModel.State(false, false, false)),
        onTryAgain = {  },
        onEvent = {}
    )
}