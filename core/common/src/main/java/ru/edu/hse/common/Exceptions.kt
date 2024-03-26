package ru.edu.hse.common

/**
 * Exception that is handled by application.
 */
open class AppException(
    message: String = "",
    cause: Throwable? = null
) : Exception(message, cause)

/**
 * Exception for authentication problems.
 */
class AuthenticationException(cause: Exception? = null) : AppException(cause = cause)

/**
 * Exception for cases when permissions not granted.
 */
class PermissionsNotGrantedException : AppException()

/**
 * Exception with message that can be shown to user.
 */
open class UserFriendlyException(
    val userFriendlyMessage: String,
    cause: Exception? = null
) : AppException(cause?.message ?: "", cause)
