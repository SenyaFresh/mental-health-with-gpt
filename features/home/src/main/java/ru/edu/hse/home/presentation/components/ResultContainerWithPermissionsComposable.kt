package ru.edu.hse.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.Core
import ru.edu.hse.common.PermissionsNotGrantedException
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.presentation.R
import ru.edu.hse.themes.DefaultButton
import ru.edu.hse.themes.DefaultText

@Composable
fun ResultContainerWithPermissionsComposable(
    container: ResultContainer<*>,
    onTryAgain: () -> Unit,
    onPermissionsLaunch: () -> Unit,
    onRestartApp: () -> Unit,
    onSuccess: @Composable () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp)
    ) {
        when (container) {
            is ResultContainer.Success -> {
                onSuccess()
            }

            is ResultContainer.Error -> {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    DefaultText(text = Core.errorHandler.getUserFriendlyMessage(container.exception))

                    Spacer(modifier = Modifier.height(8.dp))

                    when (container.exception) {
                        is AuthenticationException -> {
                            DefaultButton(
                                text = Core.resources.getString(R.string.core_presentation_logout),
                                onClick = { onRestartApp() })
                        }

                        is PermissionsNotGrantedException -> {
                            DefaultButton(
                                text = "Дать разрешения",
                                onClick = { onPermissionsLaunch() }
                            )
                        }

                        else -> {
                            DefaultButton(
                                text = Core.resources.getString(R.string.core_presentation_try_again),
                                onClick = { onTryAgain() })
                        }
                    }
                }
            }

            is ResultContainer.Pending -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Black)
                }
            }
        }
    }

}