package ru.edu.hse.mentalhealthwithgpt.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.sign_in.presentation.screens.SignInScreen
import ru.edu.hse.sign_in.presentation.viewmodels.SignInViewModel
import ru.edu.hse.sign_up.presentation.screens.SignUpScreen
import ru.edu.hse.sign_up.presentation.viewmodels.SignUpViewModel

@Composable
fun AuthNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SignInScreen.route) {

        composable(route = Screen.SignInScreen.route) {
            val signInViewModel: SignInViewModel = hiltViewModel()

            val signInState = signInViewModel.stateFlow.collectAsState(initial = ResultContainer.Pending)

            SignInScreen(
                container = signInState.value,
                onTryAgain = { signInViewModel.load() },
                onEvent = signInViewModel::onEvent
            )
        }

        composable(route = Screen.SignUpScreen.route) {
            val signUpViewModel: SignUpViewModel = hiltViewModel()

            val signUpState = signUpViewModel.stateFlow.collectAsState(
                initial = SignUpViewModel.State(
                    isInProgress = false,
                    emailError = false,
                    usernameError = false,
                    passwordError = false
                )
            )

            SignUpScreen(state = signUpState.value, onEvent = signUpViewModel::onEvent)
        }

        composable(route = Screen.TabsNavigatorScreen.route) {
            TabsNavigation()
        }
    }
}