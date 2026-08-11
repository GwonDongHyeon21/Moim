package com.moim.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Login : NavKey

@Serializable
data object Home : NavKey

@Serializable
data object User : NavKey

@Serializable
data class RoomDetail(val roomId: Long) : NavKey

@Serializable
data class Vote(val roomId: Long, val category: String) : NavKey

@Serializable
data class CandidateCreate(val roomId: Long) : NavKey

@Serializable
data class CandidateUpdate(val category: String) : NavKey