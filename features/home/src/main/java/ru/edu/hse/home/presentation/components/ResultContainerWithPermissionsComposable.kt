package ru.edu.hse.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.Core
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.exceptions.PermissionsNotGrantedException
import ru.edu.hse.presentation.R
import ru.edu.hse.themes.DefaultButton
import ru.edu.hse.themes.DefaultText

@Composable
fun ResultContainerWithPermissionsComposable(
    container: ResultContainer<*>,
    onTryAgain: () -> Unit,
    onPermissionsLaunch: () -> Unit,
    onSuccess: @Composable () -> Unit
) {

    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
        when (container) {
            is ResultContainer.Success -> {
                onSuccess()
            }

            is ResultContainer.Error -> {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    DefaultText(text = Core.errorHandler.getUserFriendlyMessage(container.exception))
                    when (container.exception) {
                        is AuthenticationException -> {
                            DefaultButton(
                                text = Core.resources.getString(R.string.core_presentation_logout),
                                onClick = { Core.appRestarter.restartApp() })
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
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }

}