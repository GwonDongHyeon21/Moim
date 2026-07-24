package com.moim.presentation.screen.login.model

import com.moim.presentation.util.snackbar.SnackBarEvent

sealed interface GoogleLoginResult {

    data class Success(val idToken: String) : GoogleLoginResult

    data class Error(val event: SnackBarEvent) : GoogleLoginResult
}