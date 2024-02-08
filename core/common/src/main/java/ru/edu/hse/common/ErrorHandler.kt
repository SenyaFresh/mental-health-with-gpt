package ru.edu.hse.common

/**
 * Error handler for actions.
 */
interface ErrorHandler {

    /**
     * Handle [exception].
     */
    fun handleError(exception: Throwable)

    /**
     * Get user-friendly message from [exception]
     */
    fun getUserMessage(exception: Throwable)
}