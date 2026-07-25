package com.moim.presentation.screen.login

import androidx.lifecycle.viewModelScope
import com.moim.domain.feature.user.repository.UserRepository
import com.moim.presentation.base.BaseViewModel
import com.moim.presentation.screen.login.model.LoginAction
import com.moim.presentation.screen.login.model.LoginEvent
import com.moim.presentation.screen.login.model.LoginUiState
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val userRepository: UserRepository,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<LoginUiState, LoginEvent>(LoginUiState()) {

    override fun checkLoading() = uiState.value.isLoading
    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.GoogleLoginSuccess -> getGoogleIdToken(action.idToken)
            is LoginAction.GoogleLoginError -> showSnackBar(action.event)
        }
    }

    private fun getGoogleIdToken(idToken: String) = doAction {
        userRepository.loginWithGoogle(idToken)
            .onSuccess {
                sendEvent(LoginEvent.NavigateToHome)
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.GOOGLE_LOGIN_ERROR)

                Timber.e(exception)
            }
    }

    private fun showSnackBar(event: SnackBarEvent) =
        viewModelScope.launch { snackBarManager.show(event) }
}