package com.moim.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<E>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    protected fun updateState(state: S.() -> S) = _uiState.update(state)

    protected fun sendEvent(event: E) = _uiEvent.trySend(event)

    protected abstract fun checkLoading(): Boolean

    protected abstract fun updateLoading(isLoading: Boolean)

    protected fun doAction(
        customCheck: (() -> Boolean)? = null,
        customUpdate: ((Boolean) -> Unit)? = null,
        action: suspend () -> Unit
    ) {
        val isLoading = customCheck?.invoke() ?: checkLoading()
        if (isLoading) return

        viewModelScope.launch {
            try {
                customUpdate?.invoke(true) ?: updateLoading(true)
                action()
            } finally {
                customUpdate?.invoke(false) ?: updateLoading(false)
            }
        }
    }
}