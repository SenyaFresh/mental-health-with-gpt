package ru.edu.hse.sign_up.presentation.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.presentation.BaseViewModel
import ru.edu.hse.sign_up.R
import ru.edu.hse.sign_up.domain.exceptions.EmptyEmailException
import ru.edu.hse.sign_up.domain.exceptions.EmptyPasswordException
import ru.edu.hse.sign_up.domain.exceptions.EmptyUsernameException
import ru.edu.hse.sign_up.domain.usecases.SignUpUseCase
import ru.edu.hse.sign_up.presentation.events.SignUpEvent
import ru.edu.hse.sign_up.presentation.routers.SignUpRouter
import javax.inject.Inject

// TODO("Inject router.")
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val router: SignUpRouter
) : BaseViewModel() {

    private val progressStateFlow = MutableStateFlow(false)
    private val emailErrorStateFlow = MutableStateFlow(false)
    private val usernameErrorStateFlow = MutableStateFlow(false)
    private val passwordErrorStateFlow = MutableStateFlow(false)

    val stateFlow = combine(
        progressStateFlow,
        emailErrorStateFlow,
        usernameErrorStateFlow,
        passwordErrorStateFlow,
        ::merge
    )

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUp -> signUp(event.email, event.username, event.password)
            is SignUpEvent.DisableEmailError -> emailErrorStateFlow.value = false
            is SignUpEvent.DisableUsernameError -> passwordErrorStateFlow.value = false
            is SignUpEvent.DisablePasswordError -> passwordErrorStateFlow.value = false
        }
    }

    private fun signUp(email: String, username: String, password: String) = debounce {
        viewModelScope.launch {
            try {
                progressStateFlow.value = true
                signUpUseCase.signUp(email, username, password)
                router.launchMain()
            } catch (e: EmptyEmailException) {
                emailErrorStateFlow.value = true
                toaster.showToast(resources.getString(R.string.feature_sign_up_empty_email))
            } catch (e: EmptyUsernameException) {
                usernameErrorStateFlow.value = true
                toaster.showToast(resources.getString(R.string.feature_sign_up_empty_username))
            } catch (e: EmptyPasswordException) {
                passwordErrorStateFlow.value = true
                toaster.showToast(resources.getString(R.string.feature_sign_up_empty_password))
            } catch (e: AuthenticationException) {
                toaster.showToast(resources.getString(R.string.feature_sign_up_invalid_data))
            } finally {
                progressStateFlow.value = false
            }
        }
    }

    private fun merge(
        isInProgress: Boolean,
        emailError: Boolean,
        usernameError: Boolean,
        passwordError: Boolean
    ) : State {
        return State(isInProgress, emailError, usernameError, passwordError)
    }

    class State(
        private val isInProgress: Boolean,
        val emailError: Boolean,
        val usernameError: Boolean,
        val passwordError: Boolean,
    ) {
        val enableButtons: Boolean get() = !isInProgress
        val showProgressBar: Boolean get() = isInProgress
    }
}