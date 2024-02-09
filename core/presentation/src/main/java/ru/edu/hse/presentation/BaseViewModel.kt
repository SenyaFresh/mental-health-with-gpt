package ru.edu.hse.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import ru.edu.hse.common.Core
import ru.edu.hse.common.Logger
import ru.edu.hse.common.Resources
import ru.edu.hse.common.Toaster

typealias Action = () -> Unit

@OptIn(FlowPreview::class)
open class BaseViewModel: ViewModel() {

    /**
     * Base viewModelScope that can handle errors using [Core.errorHandler]
     */
    protected val viewModelScope: CoroutineScope by lazy {
        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            Core.errorHandler.handleError(throwable)
        }
        CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
    }

    protected val resources: Resources get() = Core.resources

    protected val toaster: Toaster get() = Core.toaster

    protected val logger: Logger get() = Core.logger

    private val debounceFlow = MutableSharedFlow<Action>(
        replay = 1,
        extraBufferCapacity = 20
    )

    init {
        viewModelScope.launch {
            debounceFlow.sample(Core.debounceTimeoutMillis).collect {
                it()
            }
        }
    }

    /**
     * Avoid processing large number of actions received during [Core.debounceTimeoutMillis].
     * Emits only latest [action] received during [Core.debounceTimeoutMillis]
     */
    protected fun debounce(action: Action) {
        debounceFlow.tryEmit(action)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}