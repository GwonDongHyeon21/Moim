package com.moim.presentation.util.snackbar

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackBarManager @Inject constructor() {

    private val _events = Channel<SnackBarEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    suspend fun show(event: SnackBarEvent) {
        _events.send(event)
    }
}