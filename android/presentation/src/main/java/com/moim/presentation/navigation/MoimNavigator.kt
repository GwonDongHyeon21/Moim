package com.moim.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.moim.presentation.screen.candidatecreate.navigateToCandidateCreate
import com.moim.presentation.screen.candidateupdate.navigateToCandidateUpdate
import com.moim.presentation.screen.home.navigateToHome
import com.moim.presentation.screen.login.navigateToLogin
import com.moim.presentation.screen.roomdetail.navigateToRoomDetail
import com.moim.presentation.screen.vote.navigateToVote

@Stable
class MoimNavigator(
    val backStack: NavBackStack<NavKey>
) {

    val currentDestination: NavKey?
        get() = backStack.lastOrNull()

    val currentTab: MainBottomBarRoute?
        get() = MainBottomBarRoute.entries.find { tab ->
            currentDestination == tab.route
        }

    val isShowBottomBar: Boolean
        get() = currentTab != null

    fun navigateToTab(tab: MainBottomBarRoute) {
        if (currentTab == tab) return

        backStack.clear()

        if (tab != MainBottomBarRoute.HOME) {
            navigateToHome()
        }

        backStack.add(tab.route)
    }

    fun navigateToLogin() = backStack.navigateToLogin()

    fun navigateToHome() = backStack.navigateToHome()

    fun navigateToRoomDetail(roomId: Long) = backStack.navigateToRoomDetail(roomId)

    fun navigateToVote(roomId: Long, category: String) = backStack.navigateToVote(roomId, category)

    fun navigateToCandidateCreate(roomId: Long) = backStack.navigateToCandidateCreate(roomId)

    fun navigateToCandidateUpdate(category: String) = backStack.navigateToCandidateUpdate(category)

    fun popBackStack() = backStack.removeLastOrNull()
}

@Composable
fun rememberMoimNavigator(
    startDestination: NavKey
): MoimNavigator = remember(startDestination) {
    MoimNavigator(NavBackStack(startDestination))
}