package com.moim.presentation.screen.login.model

import com.moim.presentation.util.snackbar.SnackBarEvent

sealed interface LoginAction {

    data class GoogleLoginSuccess(val idToken: String) : LoginAction

    data class GoogleLoginError(val event: SnackBarEvent) : LoginAction
}