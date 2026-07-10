package com.moim.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moim.domain.repository.UserRepository
import com.moim.presentation.navigation.MainBottomBarRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _startDestination = MutableStateFlow(MainBottomBarRoute.LOGIN.route)
    val startDestination = _startDestination.asStateFlow()

    init {
        checkToken()
    }

    private fun checkToken() {
        viewModelScope.launch {
            val accessToken = userRepository.getAccessToken().first()

            if (accessToken.isNullOrEmpty()) {
                _startDestination.update { MainBottomBarRoute.LOGIN.route }
            } else {
                _startDestination.update { MainBottomBarRoute.HOME.route }
            }

            _isLoading.update { false }
        }
    }
}