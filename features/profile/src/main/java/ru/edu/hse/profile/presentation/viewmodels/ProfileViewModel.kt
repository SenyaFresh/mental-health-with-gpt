package ru.edu.hse.profile.presentation.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.presentation.BaseViewModel
import ru.edu.hse.profile.R
import ru.edu.hse.profile.domain.entities.Profile
import ru.edu.hse.profile.domain.exceptions.EmptyEmailException
import ru.edu.hse.profile.domain.exceptions.EmptyUsernameException
import ru.edu.hse.profile.domain.usecases.EditProfileUseCase
import ru.edu.hse.profile.domain.usecases.GetProfileUseCase
import ru.edu.hse.profile.domain.usecases.LogoutUseCase
import ru.edu.hse.profile.presentation.events.ProfileEvent
import ru.edu.hse.profile.presentation.routers.ProfileRouter
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val router: ProfileRouter
) : BaseViewModel() {

    private val loadProfileStateFlow = getProfileUseCase.getProfile()
    private val progressStateFlow = MutableStateFlow(false)
    private val emailErrorStateFlow = MutableStateFlow(false)
    private val usernameErrorStateFlow = MutableStateFlow(false)

    val stateFlow = combine(
        loadProfileStateFlow,
        progressStateFlow,
        emailErrorStateFlow,
        usernameErrorStateFlow,
        ::merge
    )

    fun reload() = debounce {
        getProfileUseCase.reloadProfile()
    }

    fun onEvent(profileEvent: ProfileEvent) {
        when(profileEvent) {
            is ProfileEvent.EditProfile -> editProfile(profileEvent.profile)
            ProfileEvent.DisableEmailError -> emailErrorStateFlow.value = false
            ProfileEvent.DisableUsernameError -> usernameErrorStateFlow.value = false
            ProfileEvent.Logout -> logout()
        }
    }

    private fun editProfile(profile: Profile) = debounce {
        viewModelScope.launch {
            try {
                progressStateFlow.value = true
                editProfileUseCase.editProfile(profile)
            } catch (e: EmptyEmailException) {
                emailErrorStateFlow.value = true
                toaster.showToast(resources.getString(R.string.feature_profile_empty_email))
            } catch (e: EmptyUsernameException) {
                usernameErrorStateFlow.value = true
                toaster.showToast(resources.getString(R.string.feature_profile_empty_username))
            } catch (e: AuthenticationException) {
                toaster.showToast(resources.getString(R.string.feature_profile_invalid_data))
            } finally {
                progressStateFlow.value = false
            }
        }
    }

    private fun logout() = debounce {
        viewModelScope.launch {
            logoutUseCase.logout()
            router.launchAuthScreen()
        }
    }

    private fun merge(
        loadProfileResultContainer: ResultContainer<Profile>,
        isInProgress: Boolean,
        emailError: Boolean,
        usernameError: Boolean
    ) : ResultContainer<State> {
        return loadProfileResultContainer.map { State(it, isInProgress, emailError, usernameError) }
    }

    class State(
        val profile: Profile,
        private val isInProgress: Boolean,
        val emailError: Boolean,
        val usernameError: Boolean,
    ) {
        val enableButtons: Boolean get() = !isInProgress
        val showProgressBar: Boolean get() = isInProgress
    }
}