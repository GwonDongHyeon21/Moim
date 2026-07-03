package com.moim.presentation.screen.login.model

import android.content.Context

sealed interface LoginAction {

    data class GoogleLogin(val context: Context) : LoginAction
}