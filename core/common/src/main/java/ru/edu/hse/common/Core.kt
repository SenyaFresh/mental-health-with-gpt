package ru.edu.hse.common

import kotlinx.coroutines.CoroutineScope

/**
 * Common singleton variables.
 * Call [Core.init] before using any variable.
 */
object Core {

    private lateinit var coreProvider: CoreProvider

    val appRestarter: AppRestarter get() = coreProvider.appRestarter

    val errorHandler: ErrorHandler get() = coreProvider.errorHandler

    val globalScope: CoroutineScope get() = coreProvider.globalScope

    val logger: Logger get() = coreProvider.logger

    val resources: Resources get() = coreProvider.resources

    fun init(coreProvider: CoreProvider) {
        this.coreProvider = coreProvider
    }
}