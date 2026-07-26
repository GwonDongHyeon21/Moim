package com.moim.presentation.screen.roomdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.R
import com.moim.presentation.navigation.RoomDetail
import com.moim.presentation.screen.component.MoimProgressIndicator
import com.moim.presentation.screen.component.MoimTopBar
import com.moim.presentation.screen.roomdetail.component.RoomCodeDialog
import com.moim.presentation.screen.roomdetail.model.RoomDetailAction
import com.moim.presentation.screen.roomdetail.model.RoomDetailEvent
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiState
import com.moim.presentation.util.DummyData
import com.moim.presentation.util.collectWithLifecycle

@Composable
fun RoomDetailScreen(
    route: RoomDetail,
    onNavigateToVote: (roomId: Long, category: String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RoomDetailViewModel = hiltViewModel<RoomDetailViewModel, RoomDetailViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(route)
        }
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {
            is RoomDetailEvent.NavigateToVote -> onNavigateToVote(event.roomId, event.category)

            RoomDetailEvent.NavigateBack -> onNavigateBack()
        }
    }

    RoomDetailScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier
    )

    if (uiState.isLoading) {
        MoimProgressIndicator()
    }
}

@Composable
fun RoomDetailScreen(
    uiState: RoomDetailUiState,
    onAction: (RoomDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val roomInfo = uiState.roomDetail.roomInfo
    var isExpanded by remember { mutableStateOf(false) }

    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = modifier,
        topBar = {
            MoimTopBar(
                value = roomInfo.title,
                navigationIcon = R.drawable.arrow_back_24,
                actionIcon = R.drawable.more_vert_24,
                onClickNavigationIcon = { onAction(RoomDetailAction.NavigateBack) },
                onClickActionIcon = { isExpanded = true }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { onAction(RoomDetailAction.RefreshRoomDetail(roomInfo.id!!)) },
            state = pullToRefreshState,
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = roomInfo.title)
                Text(text = roomInfo.description.toString())
                Text(text = roomInfo.currentMemberCount.toString())
                Text(text = roomInfo.maxCount.toString())
            }
        }
    }

    if (isExpanded) {
        RoomCodeDialog(
            roomCode = roomInfo.code,
            onDismissRequest = { isExpanded = false }
        )
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