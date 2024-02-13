package ru.edu.hse.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.Core
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.themes.DefaultButton
import ru.edu.hse.themes.DefaultText


/**
 * Represents a container that can:
 *
 * show progress bar when [container] is [ResultContainer.Pending];
 *
 * show error when [container] is [ResultContainer.Error] and button to handle error;
 *
 * show [onSuccess] composable when [container] is [ResultContainer.Success].
 */
@Composable
fun ResultContainerComposable(
    container: ResultContainer<*>,
    onTryAgain: () -> Unit,
    onSuccess: @Composable () -> Unit
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (container) {
            is ResultContainer.Success -> {
                onSuccess()
            }

            is ResultContainer.Error -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    DefaultText(text = Core.errorHandler.getUserFriendlyMessage(container.exception))
                    if (container.exception is AuthenticationException) {
                        DefaultButton(
                            text = Core.resources.getString(R.string.core_presentation_logout),
                            onClick = { Core.appRestarter.restartApp() })
                    } else {
                        DefaultButton(
                            text = Core.resources.getString(R.string.core_presentation_try_again),
                            onClick = { onTryAgain() })
                    }
                }
            }

            is ResultContainer.Pending -> {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }

}