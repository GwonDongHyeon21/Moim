package com.moim.presentation.util.snackbar

import androidx.annotation.StringRes
import com.moim.presentation.R

enum class SnackBarEvent(
    @param:StringRes val messageResId: Int
) {
    // Common Error
    NETWORK_ERROR(R.string.snack_bar_network_error),
    DATA_LOAD_FAILED(R.string.snack_bar_data_load_failed),
    DATA_SAVE_FAILED(R.string.snack_bar_data_save_failed),

    // Login
    GOOGLE_LOGIN_ERROR(R.string.snack_bar_google_login_error)
}