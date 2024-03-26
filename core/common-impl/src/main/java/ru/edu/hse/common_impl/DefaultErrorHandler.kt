package ru.edu.hse.common_impl

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.ErrorHandler
import ru.edu.hse.common.Logger
import ru.edu.hse.common.Resources
import ru.edu.hse.common.Toaster
import ru.edu.hse.common.UserFriendlyException

/**
 * Default realisation for [ErrorHandler]
 */
class DefaultErrorHandler(
    private val logger: Logger,
    private val resources: Resources,
    private val toaster: Toaster
) : ErrorHandler {

    /**
     * Handles AppExceptions and coroutine exceptions.
     */
    override fun handleError(exception: Throwable) {
        logger.logError(exception)
        when (exception) {
            is AuthenticationException -> handleAuthenticationException(exception)
            is UserFriendlyException -> handleUserFriendlyException(exception)
            is TimeoutCancellationException -> handleTimeoutCancellationException(exception)
            is CancellationException -> return
            else -> handleUnknownException(exception)
        }
    }

    /**
     * Gets messages from exception that can be shown to user.
     */
    override fun getUserFriendlyMessage(exception: Throwable): String {
        return when (exception) {
            is AuthenticationException -> resources.getString(R.string.core_common_exception_authentication)
            is UserFriendlyException -> exception.userFriendlyMessage
            is TimeoutCancellationException -> resources.getString(R.string.core_common_exception_timeout)
            else -> resources.getString(R.string.core_common_exception_unknown)
        }
    }

    /**
     * Handles authentication error.
     */
    private fun handleAuthenticationException(exception: AuthenticationException) {
        toaster.showToast(getUserFriendlyMessage(exception))
    }

    /**
     * Handles error with user friendly message.
     * @see UserFriendlyException
     */
    private fun handleUserFriendlyException(exception: UserFriendlyException) {
        toaster.showToast(getUserFriendlyMessage(exception))
    }

    /**
     * Handles operation timeout error.
     */
    private fun handleTimeoutCancellationException(exception: TimeoutCancellationException) {
        toaster.showToast(getUserFriendlyMessage(exception))
    }

    /**
     * Handles unknown errors.
     */
    private fun handleUnknownException(exception: Throwable) {
        toaster.showToast(getUserFriendlyMessage(exception))
    }

}