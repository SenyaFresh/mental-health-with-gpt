package ru.edu.hse.common_impl

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import ru.edu.hse.common.AppRestarter
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.ConnectionException
import ru.edu.hse.common.ErrorHandler
import ru.edu.hse.common.Logger
import ru.edu.hse.common.Resources
import ru.edu.hse.common.Toaster
import ru.edu.hse.common.UserFriendlyException

/**
 * Default realisation for [ErrorHandler]
 */
class DefaultErrorHandler(
    private val appRestarter: AppRestarter,
    private val logger: Logger,
    private val resources: Resources,
    private val toaster: Toaster
) : ErrorHandler {

    private var lastAppRestartTimestamp = 0L

    /**
     * Handles AppExceptions and coroutine exceptions.
     */
    override fun handleError(exception: Throwable) {
        logger.logError(exception)
        when (exception) {
            is ConnectionException -> handleConnectionException(exception)
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
            is ConnectionException -> resources.getString(R.string.core_common_exception_connection)
            is AuthenticationException -> resources.getString(R.string.core_common_exception_authentication)
            is UserFriendlyException -> exception.userFriendlyMessage
            is TimeoutCancellationException -> resources.getString(R.string.core_common_exception_timeout)
            else -> resources.getString(R.string.core_common_exception_unknown)
        }
    }

    /**
     * Handles connection error.
     */
    private fun handleConnectionException(exception: ConnectionException) {
        toaster.showToast(getUserFriendlyMessage(exception))
    }

    /**
     * Handles authentication error. Restarts app again only after [RESTART_TIMEOUT] to
     * to prevent restart loop.
     */
    private fun handleAuthenticationException(exception: AuthenticationException) {
        if (System.currentTimeMillis() - lastAppRestartTimestamp > RESTART_TIMEOUT) {
            toaster.showToast(getUserFriendlyMessage(exception))
            lastAppRestartTimestamp = System.currentTimeMillis()
            appRestarter.restartApp()
        }
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

    private companion object {
        const val RESTART_TIMEOUT = 10000L
    }
}