package ru.edu.hse.profile.presentation.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.components.DefaultButton
import ru.edu.hse.components.DefaultTextField
import ru.edu.hse.components.DefaultTitle
import ru.edu.hse.components.EmailIcon
import ru.edu.hse.components.NameIcon
import ru.edu.hse.presentation.ResultContainerComposable
import ru.edu.hse.profile.R
import ru.edu.hse.profile.domain.entities.Profile
import ru.edu.hse.profile.presentation.events.ProfileEvent
import ru.edu.hse.profile.presentation.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    container: ResultContainer<ProfileViewModel.State>,
    onTryAgain: () -> Unit,
    onEvent: (ProfileEvent) -> Unit,
    onRestartApp: () -> Unit
) {

    ResultContainerComposable(
        container = container,
        onTryAgain = onTryAgain,
        onRestartApp = onRestartApp
    ) {

        var email by rememberSaveable {
            mutableStateOf(container.unwrap().profile.email)
        }

        var username by rememberSaveable {
            mutableStateOf(container.unwrap().profile.username)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp, start = 30.dp, end = 30.dp)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DefaultTitle(modifier = Modifier.padding(bottom = 20.dp), text = stringResource(R.string.profile_title))

            DefaultTextField(
                modifier = Modifier.padding(bottom = 20.dp),
                value = email,
                onValueChange = {
                    email = it
                    onEvent(ProfileEvent.DisableEmailError)
                },
                icon = { EmailIcon() },
                label = stringResource(R.string.email_label),
                hint = stringResource(R.string.email_hint),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                error = container.unwrap().emailError
            )

            DefaultTextField(
                modifier = Modifier.padding(bottom = 20.dp),
                value = username,
                onValueChange = {
                    username = it
                    onEvent(ProfileEvent.DisableUsernameError)
                },
                icon = { NameIcon() },
                label = stringResource(R.string.username_title),
                hint = stringResource(R.string.username_hint),
                error = container.unwrap().usernameError
            )

            DefaultButton(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .height(45.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.save),
                enabled = container.unwrap().enableButtons,
                onClick = { onEvent(ProfileEvent.EditProfile(Profile(email, username))) }
            )

            DefaultButton(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .height(45.dp),
                text = stringResource(R.string.logout),
                containerColor = Color.Transparent,
                enabled = container.unwrap().enableButtons,
                onClick = {
                    onEvent(ProfileEvent.Logout)
                    onRestartApp()
                }
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
fun ProfileScreenPreview() {
    ProfileScreen(
        ResultContainer.Done(
            ProfileViewModel.State(
                Profile("test@gmail.com", "test"),
                isInProgress = false,
                emailError = false,
                usernameError = false
            )
        ), { }, { }, { }
    )
}