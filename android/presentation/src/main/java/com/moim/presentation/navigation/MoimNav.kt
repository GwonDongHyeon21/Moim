package com.moim.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.moim.presentation.navigation.MoimNavConstant.fadeTransition
import com.moim.presentation.navigation.MoimNavConstant.slideTransition
import com.moim.presentation.screen.home.home
import com.moim.presentation.screen.login.login
import com.moim.presentation.screen.roomdetail.roomDetail
import com.moim.presentation.screen.vote.vote

private object MoimNavConstant {
    val fadeTransition = NavDisplay.transitionSpec { fadeIn() togetherWith fadeOut() }
    val slideTransition = slideInHorizontally(initialOffsetX = { it }) togetherWith
            slideOutHorizontally(targetOffsetX = { -it })
}

@Composable
fun MoimNav(
    navigator: MoimNavigator,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        backStack = navigator.backStack,
        onBack = navigator::popBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        transitionSpec = { slideTransition },
        popTransitionSpec = { slideTransition },
        predictivePopTransitionSpec = { slideTransition },
        entryProvider = entryProvider {
            login(
                onNavigateToHome = navigator::navigateToHome,
                modifier = modifier.padding(innerPadding)
            )

            home(
                metadata = fadeTransition,
                onNavigateToRoomDetail = { navigator.navigateToRoomDetail(it) },
                modifier = modifier.padding(innerPadding)
            )

            roomDetail(
                metadata = fadeTransition,
                onNavigateToVote = { roomId, category ->
                    navigator.navigateToVote(roomId, category)
                },
                onNavigateBack = navigator::popBackStack,
                modifier = modifier.padding(innerPadding)
            )

            vote(
                metadata = fadeTransition,
                onNavigateBack = navigator::popBackStack,
                modifier = modifier.padding(innerPadding)
            )
        }
    )
}