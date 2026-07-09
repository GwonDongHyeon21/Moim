package com.moim.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.screen.home.component.RoomCard
import com.moim.presentation.screen.home.model.HomeAction
import com.moim.presentation.screen.home.model.HomeEvent
import com.moim.presentation.screen.home.model.HomeUiState
import com.moim.presentation.util.DummyData
import com.moim.presentation.util.collectWithLifecycle

@Composable
fun HomeScreen(
    onNavigateToRoomDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {
            is HomeEvent.NavigateToRoomDetail -> onNavigateToRoomDetail(event.roomCode)
            is HomeEvent.ShowSnackBar -> {}
        }
    }

    HomeScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = uiState.rooms,
                key = { it.code }
            ) { room ->
                RoomCard(
                    room = room,
                    onClick = { onAction(HomeAction.ClickRoom(room.code)) }
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