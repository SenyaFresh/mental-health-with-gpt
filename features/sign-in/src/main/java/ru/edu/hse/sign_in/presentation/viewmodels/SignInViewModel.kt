package ru.edu.hse.sign_in.presentation.viewmodels

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.presentation.BaseViewModel
import ru.edu.hse.sign_in.domain.exceptions.EmptyEmailException
import ru.edu.hse.sign_in.domain.exceptions.EmptyPasswordException
import ru.edu.hse.sign_in.domain.usecases.CheckIfSignedInUseCase
import ru.edu.hse.sign_in.domain.usecases.SignInUseCase
import ru.edu.hse.sign_in.presentation.events.SignInEvent
import ru.edu.hse.sign_in.presentation.routers.SignInRouter
import ru.edu.hse.sing_in.R
import javax.inject.Inject

// TODO("Inject router.")
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val checkIfSignedInUseCase: CheckIfSignedInUseCase,
    private val signInUseCase: SignInUseCase,
    private val router: SignInRouter
) : BaseViewModel() {

    private val loadScreenStateFlow = MutableStateFlow<ResultContainer<Unit>>(ResultContainer.Pending)
    private val progressStateFlow = MutableStateFlow(false)
    private val emailErrorStateFlow = MutableStateFlow(false)
    private val passwordErrorStateFlow = MutableStateFlow(false)

    val stateFlow = combine(
        loadScreenStateFlow,
        progressStateFlow,
        emailErrorStateFlow,
        passwordErrorStateFlow,
        ::merge
    )

    init {
        load()
    }

    fun load() = debounce {
        viewModelScope.launch {
            try {
                loadScreenStateFlow.value = ResultContainer.Pending
                if (checkIfSignedInUseCase.isSignedIn()) {
                    router.launchMain()
                } else {
                    loadScreenStateFlow.value = ResultContainer.Success(Unit)
                }
            } catch (e: Exception) {
                loadScreenStateFlow.value = ResultContainer.Error(e)
            }
        }
    }

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignIn -> signIn(event.email, event.password)
            is SignInEvent.LaunchSignUp ->  debounce { router.launchSignUp() }
            is SignInEvent.DisableEmailError -> emailErrorStateFlow.value = false
            is SignInEvent.DisablePasswordError -> passwordErrorStateFlow.value = false
        }
    }

    private fun signIn(email: String, password: String) = debounce {
        viewModelScope.launch {
            try {
                progressStateFlow.value = true
                signInUseCase.signIn(email, password)
                router.launchMain()
            } catch (e: EmptyEmailException) {
                emailErrorStateFlow.value = true
                toaster.showToast(resources.getString(R.string.feature_sign_in_empty_email))
            } catch (e: EmptyPasswordException) {
                emailErrorStateFlow.value = true
                toaster.showToast(resources.getString(R.string.feature_sign_in_empty_password))
            } catch (e: AuthenticationException) {
                toaster.showToast(resources.getString(R.string.feature_sign_in_invalid_data))
            } finally {
                progressStateFlow.value = false
            }
        }
    }

    private fun merge(
        loadResultContainer: ResultContainer<Unit>,
        isInProgress: Boolean,
        emailError: Boolean,
        passwordError: Boolean
    ) : ResultContainer<State> {
        return loadResultContainer.map { State(isInProgress, emailError, passwordError) }
    }

    class State(
        private val isInProgress: Boolean,
        val emailError: Boolean,
        val passwordError: Boolean,
    ) {
        val enableButtons: Boolean get() = !isInProgress
        val showProgressBar: Boolean get() = isInProgress
    }
}