package com.moim.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.R
import com.moim.presentation.screen.login.model.LoginAction
import com.moim.presentation.screen.login.model.LoginEvent
import com.moim.presentation.screen.login.model.LoginUiState
import com.moim.presentation.util.collectWithLifecycle

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {
            LoginEvent.NavigateToHome -> {
                onNavigateToHome()
            }

            is LoginEvent.ShowSnackBar -> {}
        }
    }

    LoginScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier,
            onClick = { onAction(LoginAction.GoogleLogin(context)) }
        ) {
            Text(text = stringResource(R.string.google_login))
        }
    }
}