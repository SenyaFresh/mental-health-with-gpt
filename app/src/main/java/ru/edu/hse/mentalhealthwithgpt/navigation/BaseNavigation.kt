package ru.edu.hse.mentalhealthwithgpt.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import ru.edu.hse.assistant.presentation.screens.AssistantScreen
import ru.edu.hse.assistant.presentation.viewmodels.AssistantViewModel
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.presentation.screens.HomeScreen
import ru.edu.hse.home.presentation.viewmodels.HomeViewModel
import ru.edu.hse.mentalhealthwithgpt.R
import ru.edu.hse.mentalhealthwithgpt.components.PrivacyPolicyScreen
import ru.edu.hse.profile.presentation.screens.ProfileScreen
import ru.edu.hse.profile.presentation.viewmodels.ProfileViewModel
import ru.edu.hse.sign_in.presentation.screens.SignInScreen
import ru.edu.hse.sign_in.presentation.viewmodels.SignInViewModel
import ru.edu.hse.sign_up.presentation.screens.SignUpScreen
import ru.edu.hse.sign_up.presentation.viewmodels.SignUpViewModel

@Composable
fun BaseNavigation() {

    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    val currentRote = backstackState?.destination?.route

    val bottomBarVisibility = remember(key1 = backstackState) {
        backstackState?.destination?.route == Screen.AssistantScreen.route ||
                backstackState?.destination?.route == Screen.HomeScreen.route ||
                backstackState?.destination?.route == Screen.ProfileScreen.route
    }

    selectedItem = remember(key1 = backstackState) {
        when (currentRote) {
            Screen.AssistantScreen.route -> 0
            Screen.HomeScreen.route -> 1
            Screen.ProfileScreen.route -> 2
            else -> 1
        }
    }

    val bottomNavigationItems = listOf(
        BaseBottomNavigationItem(R.drawable.ic_question, Screen.AssistantScreen) { screen ->
            navigateToTab(
                screen,
                navController
            )
        },
        BaseBottomNavigationItem(R.drawable.ic_home, Screen.HomeScreen) { screen ->
            navigateToTab(
                screen,
                navController
            )
        },
        BaseBottomNavigationItem(R.drawable.ic_profile, Screen.ProfileScreen) { screen ->
            navigateToTab(
                screen,
                navController
            )
        },
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (bottomBarVisibility) {
                BaseBottomNavigationBar(
                    items = bottomNavigationItems,
                    selected = selectedItem
                )
            }
        }
    ) {
        NavHost(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            navController = navController,
            startDestination = Screen.SignInScreen.route
        ) {

            composable(
                route = Screen.PrivacyPolicyScreen.route,
                deepLinks = listOf(
                    navDeepLink {
                        action = "androidx.health.ACTION_SHOW_PERMISSIONS_RATIONALE"
                    }
                )
            ) {
                PrivacyPolicyScreen()
            }

            composable(route = Screen.SignInScreen.route) {
                val signInViewModel: SignInViewModel = hiltViewModel()

                val signInState =
                    signInViewModel.stateFlow.collectAsState(initial = ResultContainer.Pending)
                val launchMainState = signInViewModel.launchMainStateFlow.collectAsState()

                SignInScreen(
                    container = signInState.value,
                    onTryAgain = { signInViewModel.load() },
                    onEvent = signInViewModel::onEvent,
                    launchMainFlag = launchMainState.value,
                    onLaunchMain = { navController.navigate(Screen.HomeScreen.route) },
                    onLaunchSignUp = { navController.navigate(Screen.SignUpScreen.route) },
                    onRestartApp = { restartApp(navController) }
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
                val launchMainState = signUpViewModel.launchMainStateFlow.collectAsState()

                SignUpScreen(
                    state = signUpState.value,
                    onEvent = signUpViewModel::onEvent,
                    launchMainFlag = launchMainState.value,
                    onLaunchMain = { navController.navigate(Screen.HomeScreen.route) },
                    onRestartApp = { restartApp(navController) }
                )
            }


            composable(Screen.AssistantScreen.route) {
                val assistantViewModel: AssistantViewModel = hiltViewModel()

                val responseState = assistantViewModel.response.collectAsState()

                AssistantScreen(
                    responseContainer = responseState.value,
                    onGetResponse = assistantViewModel::getResponse,
                    onRestartApp = { restartApp(navController) }
                )
            }

            composable(Screen.HomeScreen.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()

                val permissions = homeViewModel.permissions
                val permissionsLauncher =
                    rememberLauncherForActivityResult(homeViewModel.permissionsLauncher) {
                        homeViewModel.reloadHealthData()
                    }

                val healthDataState = homeViewModel.healthDataStateFlow.collectAsState()
                val everydayMissionsState = homeViewModel.everydayMissionsStateFlow.collectAsState()
                val mentalTestState = homeViewModel.mentalTestStateFlow.collectAsState()

                HomeScreen(
                    healthContainer = healthDataState.value,
                    missionsContainer = everydayMissionsState.value,
                    mentalTestContainer = mentalTestState.value,
                    onPermissionsLaunch = { permissionsLauncher.launch(permissions) },
                    onEvent = homeViewModel::onEvent,
                    onRestartApp = { restartApp(navController) }
                )
            }

            composable(Screen.ProfileScreen.route) {
                val profileViewModel: ProfileViewModel = hiltViewModel()

                val profileState =
                    profileViewModel.stateFlow.collectAsState(initial = ResultContainer.Pending)

                ProfileScreen(
                    container = profileState.value,
                    onTryAgain = profileViewModel::reload,
                    onEvent = profileViewModel::onEvent,
                    onRestartApp = { restartApp(navController) }
                )
            }
        }
    }
}

private fun navigateToTab(tab: Screen, navController: NavController) {
    navController.navigate(tab.route) {
        navController.graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun restartApp(navController: NavController) {
    navController.popBackStack(navController.graph.startDestinationId, false)
    navController.navigate(Screen.SignInScreen.route) {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true
        }
    }
}