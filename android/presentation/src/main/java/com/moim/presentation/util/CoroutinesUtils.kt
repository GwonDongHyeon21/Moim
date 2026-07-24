package com.moim.presentation.util

import kotlinx.coroutines.flow.SharingStarted

private const val StopTimeoutMillis: Long = 5000

val WhileUiSubscribed = SharingStarted.WhileSubscribed(StopTimeoutMillis)