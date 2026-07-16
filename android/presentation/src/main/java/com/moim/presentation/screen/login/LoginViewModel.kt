package com.moim.presentation.screen.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.moim.domain.repository.UserRepository
import com.moim.presentation.BuildConfig
import com.moim.presentation.screen.login.model.LoginAction
import com.moim.presentation.screen.login.model.LoginEvent
import com.moim.presentation.screen.login.model.LoginUiState
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val userRepository: UserRepository,
    private val snackBarManager: SnackBarManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginEvent>(BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.GoogleLogin -> getGoogleIdToken(action.context)
        }
    }

    private fun getGoogleIdToken(context: Context) {
        viewModelScope.launch {
            val credentialManager = CredentialManager.create(context)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            runCatching {
                credentialManager.getCredential(context = context, request = request)
            }.onSuccess { result ->
                _uiState.update { it.copy(isLoading = true) }

                val credential = result.credential

                if (credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken

                    userRepository.loginWithGoogle(idToken)
                        .onSuccess {
                            _uiEvent.trySend(LoginEvent.NavigateToHome)
                        }.onFailure { exception ->
                            _uiState.update { it.copy(isLoading = false) }
                            snackBarManager.show(SnackBarEvent.GOOGLE_LOGIN_ERROR)

                            Timber.e(exception)
                            FirebaseCrashlytics.getInstance().recordException(exception)
                        }
                }
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.GOOGLE_LOGIN_ERROR)

                Timber.e(exception)
                FirebaseCrashlytics.getInstance().recordException(exception)
            }
        }
    }
}