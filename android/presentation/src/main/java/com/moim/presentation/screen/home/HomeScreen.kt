package com.moim.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.R
import com.moim.presentation.screen.component.MoimProgressIndicator
import com.moim.presentation.screen.component.MoimTopBar
import com.moim.presentation.screen.home.component.CreateRoomDialog
import com.moim.presentation.screen.home.component.JoinRoomDialog
import com.moim.presentation.screen.home.component.RoomCard
import com.moim.presentation.screen.home.component.RoomFloatingActionButton
import com.moim.presentation.screen.home.model.HomeAction
import com.moim.presentation.screen.home.model.HomeEvent
import com.moim.presentation.screen.home.model.HomeUiState
import com.moim.presentation.screen.home.model.RoomOptions
import com.moim.presentation.util.DummyData
import com.moim.presentation.util.collectWithLifecycle

@Composable
fun HomeScreen(
    onNavigateToRoomDetail: (Long) -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {
            is HomeEvent.NavigateToRoomDetail -> onNavigateToRoomDetail(event.roomId)
            HomeEvent.NavigateToLogin -> onNavigateToLogin()
            is HomeEvent.ShowSnackBar -> {}
        }
    }

    HomeScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier
    )

    if (uiState.isLoading) {
        MoimProgressIndicator()
    }
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = modifier,
        topBar = {
            MoimTopBar(
                value = "",
                actionIcon = R.drawable.logout_24,
                onClickActionIcon = { onAction(HomeAction.Logout) },
            )
        },
        floatingActionButton = {
            RoomFloatingActionButton { option ->
                when (option) {
                    RoomOptions.CREATE -> {
                        onAction(HomeAction.ClickDialog(true, RoomOptions.CREATE.name))
                    }

                    RoomOptions.JOIN -> {
                        onAction(HomeAction.ClickDialog(true, RoomOptions.JOIN.name))
                    }
                }
            }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { onAction(HomeAction.RefreshHome) },
            state = pullToRefreshState,
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = uiState.rooms,
                        key = { it.code }
                    ) { room ->
                        RoomCard(
                            room = room,
                            onClick = { onAction(HomeAction.ClickRoom(room.id!!)) }
                        )
                    }
                }
            }
        }
    }

    if (uiState.isExpanded) {
        when (uiState.roomOption) {
            RoomOptions.CREATE.name -> {
                CreateRoomDialog(
                    title = uiState.title,
                    description = uiState.description,
                    onTitleChanged = { onAction(HomeAction.OnTitleChanged(it)) },
                    onDescriptionChanged = { onAction(HomeAction.OnDescriptionChanged(it)) },
                    onConfirm = {
                        onAction(HomeAction.CreateRoom(it))
                    },
                    onDismissRequest = {
                        onAction(HomeAction.ClickDialog(false, ""))
                    }
                )
            }

            RoomOptions.JOIN.name -> {
                JoinRoomDialog(
                    onConfirm = {
                        onAction(HomeAction.JoinRoom(it))
                    },
                    onDismissRequest = {
                        onAction(HomeAction.ClickDialog(false, ""))
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeUiState(rooms = DummyData.dummyRooms),
        onAction = {}
    )
}