package com.moim.presentation.screen.roomdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.navigation.RoomDetail
import com.moim.presentation.screen.roomdetail.model.RoomDetailAction
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiState
import com.moim.presentation.util.DummyData
import com.moim.presentation.util.collectWithLifecycle

@Composable
fun RoomDetailScreen(
    route: RoomDetail,
    modifier: Modifier = Modifier,
    viewModel: RoomDetailViewModel = hiltViewModel<RoomDetailViewModel, RoomDetailViewModel.Factory>(
        creationCallback = { factory -> factory.create(route) }
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {

        }
    }

    RoomDetailScreen(
        uiState = uiState,
        onAction = {},
        modifier = modifier
    )
}

@Composable
fun RoomDetailScreen(
    uiState: RoomDetailUiState,
    onAction: (RoomDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val roomInfo = uiState.roomDetail.roomInfo

    Column(modifier = modifier.fillMaxSize()) {
        Text(text = roomInfo.title)
        Text(text = roomInfo.description.toString())
        Text(text = roomInfo.currentMemberCount.toString())
        Text(text = roomInfo.maxCount.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun RoomDetailScreenPreview() {
    RoomDetailScreen(
        uiState = RoomDetailUiState(roomDetail = DummyData.dummyRoomDetail),
        onAction = {}
    )
}