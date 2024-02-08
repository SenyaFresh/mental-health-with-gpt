package ru.edu.hse.common

/**
 * Exception that is handled by application.
 */
open class AppException(
    message: String = "",
    cause: Throwable? = null
) : Exception(message, cause)

/**
 * Exception for internet problems.
 */
class ConnectionException(cause: Exception) : AppException(cause = cause)

/**
 * Exception for authentication problems.
 */
class AuthenticationException(cause: Exception? = null) : AppException(cause = cause)

/**
 * Exception for cases when something does not exist.
 */
class NotFoundException : AppException()

/**
 * Exception with message that can be shown to user.
 */
class UserFriendlyException(
    val userFriendlyMessage: String,
    cause: Exception
) : AppException(cause.message ?: "", cause)
