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
    GOOGLE_LOGIN_ERROR(R.string.snack_bar_google_login_error),
    USER_LOGIN_CANCEL(R.string.snack_bar_user_login_cancel),
    NOT_FOUND_GOOGLE_ACCOUNT(R.string.snack_bar_not_found_google_account),

    // Candidate Create
    CANDIDATE_COUNT_LIMIT(R.string.snack_bar_candidate_count_limit)
}