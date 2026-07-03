package com.moim.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.moim.presentation.screen.home.navigateToHome

@Stable
class MoimNavigator(
    val backStack: NavBackStack<NavKey>
) {
    val currentDestination: Any?
        get() = backStack.lastOrNull()

    val currentTab: MainBottomBarRoute?
        get() = MainBottomBarRoute.entries.find { tab ->
            currentDestination == tab.route
        }

    fun navigateToTab(tab: MainBottomBarRoute) {
        if (currentTab == tab) return

        backStack.clear()

        if (tab != MainBottomBarRoute.HOME) {
            navigateToHome()
        }

        backStack.add(tab.route)
    }

    fun navigateToHome() = backStack.navigateToHome()

    fun popBackStack() = backStack.removeLastOrNull()
}

@Composable
fun rememberMoimNavigator(
    backStack: NavBackStack<NavKey> = rememberNavBackStack(MainBottomBarRoute.LOGIN.route)
): MoimNavigator = remember(backStack) {
    MoimNavigator(backStack)
}