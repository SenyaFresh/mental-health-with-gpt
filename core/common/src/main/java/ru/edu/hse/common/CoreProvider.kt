package ru.edu.hse.common

import kotlinx.coroutines.CoroutineScope

/**
 * Provides global entities.
 */
interface CoreProvider {

    val appRestarter: AppRestarter

    val errorHandler: ErrorHandler

    val globalScope: CoroutineScope

    val logger: Logger

    val resources: Resources

}