package ru.edu.hse.common_impl

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import ru.edu.hse.common.AppRestarter
import ru.edu.hse.common.CoreProvider
import ru.edu.hse.common.ErrorHandler
import ru.edu.hse.common.Logger
import ru.edu.hse.common.Resources
import ru.edu.hse.common.Toaster

class DefaultCoreProvider(
    private val appContext: Context,
    override val appRestarter: AppRestarter,
    override val resources: Resources = AndroidResources(appContext),
    override val globalScope: CoroutineScope = createDefaultGlobalScope(),
    override val toaster: Toaster = AndroidToaster(appContext),
    override val logger: Logger = AndroidLogger(),
    override val errorHandler: ErrorHandler = DefaultErrorHandler(
        appRestarter,
        logger,
        resources,
        toaster
    )
) : CoreProvider