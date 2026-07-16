package com.moim.presentation.screen.login

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.moim.presentation.BuildConfig
import com.moim.presentation.R
import com.moim.presentation.screen.component.MoimProgressIndicator
import com.moim.presentation.screen.login.model.GoogleLoginResult
import com.moim.presentation.screen.login.model.LoginAction
import com.moim.presentation.screen.login.model.LoginEvent
import com.moim.presentation.screen.login.model.LoginUiState
import com.moim.presentation.util.collectWithLifecycle
import com.moim.presentation.util.snackbar.SnackBarEvent
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {
            LoginEvent.NavigateToHome -> onNavigateToHome()
            is LoginEvent.ShowSnackBar -> {}
        }
    }

    LoginScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier
    )

    if (uiState.isLoading) {
        MoimProgressIndicator()
    }
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier,
            onClick = {
                coroutineScope.launch {
                    when (val result = getGoogleIdToken(context)) {
                        is GoogleLoginResult.Success -> onAction(LoginAction.GoogleLoginSuccess(result.idToken))
                        is GoogleLoginResult.Error -> onAction(LoginAction.GoogleLoginError(result.event))
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.google_login))
        }
    }
}

private suspend fun getGoogleIdToken(context: Context): GoogleLoginResult {
    return try {
        val credentialManager = CredentialManager.create(context)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = credentialManager.getCredential(context, request)
        val credential = result.credential

        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val idToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
            GoogleLoginResult.Success(idToken)
        } else {
            GoogleLoginResult.Error(SnackBarEvent.GOOGLE_LOGIN_ERROR)
        }
    } catch (e: GetCredentialCancellationException) {
        Timber.e(e)
        GoogleLoginResult.Error(SnackBarEvent.USER_LOGIN_CANCEL)
    } catch (e: NoCredentialException) {
        Timber.e(e)
        GoogleLoginResult.Error(SnackBarEvent.NOT_FOUND_GOOGLE_ACCOUNT)
    } catch (e: Exception) {
        Timber.e(e)
        GoogleLoginResult.Error(SnackBarEvent.GOOGLE_LOGIN_ERROR)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(),
        onAction = {}
    )
}