package com.moim.presentation.screen.roomdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.R
import com.moim.presentation.model.RoomInfoUiModel
import com.moim.presentation.navigation.RoomDetail
import com.moim.presentation.screen.component.MoimProgressIndicator
import com.moim.presentation.screen.component.MoimTopBar
import com.moim.presentation.screen.roomdetail.component.CategoryCard
import com.moim.presentation.screen.roomdetail.component.RoomCodeDialog
import com.moim.presentation.screen.roomdetail.model.RoomDetailAction
import com.moim.presentation.screen.roomdetail.model.RoomDetailEvent
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiState
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.util.DummyData
import com.moim.presentation.util.collectWithLifecycle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun RoomDetailScreen(
    route: RoomDetail,
    onNavigateToVote: (roomId: Long, category: String) -> Unit,
    onNavigateToCandidateCreate: (Long) -> Unit,
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

            is RoomDetailEvent.NavigateToCandidateCreate -> onNavigateToCandidateCreate(event.roomId)

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
    val categoryVoteStatus = uiState.roomDetail.categoryVoteStatus

    var showRoomCode by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = modifier,
        topBar = {
            MoimTopBar(
                value = roomInfo.title,
                navigationIcon = R.drawable.arrow_back_24,
                actionIcon = R.drawable.more_vert_24,
                onClickNavigationIcon = { onAction(RoomDetailAction.NavigateBack) },
                onClickActionIcon = { showRoomCode = true }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(RoomDetailAction.NavigateToCandidateCreate) }) {
                Icon(
                    painter = painterResource(R.drawable.add_24),
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { onAction(RoomDetailAction.RefreshRoomDetail(roomInfo.id!!)) },
            state = pullToRefreshState,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = MoimPadding.AppHorizontalPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(MoimSpace.SpaceSmall)
            ) {
                RoomDetailInfoSection(roomInfo = roomInfo)

                categoryVoteStatus.forEach { category ->
                    CategoryCard(category = category) {
                        onAction(RoomDetailAction.NavigateToVote(category.category))
                    }
                }
            }
        }
    }

    if (showRoomCode) {
        RoomCodeDialog(
            roomCode = roomInfo.code,
            onDismissRequest = { showRoomCode = false }
        )
    }
}

@Composable
fun RoomDetailInfoSection(roomInfo: RoomInfoUiModel) {
    val uiFormatter =
        DateTimeFormatter.ofPattern(stringResource(R.string.ui_time_format), Locale.KOREA)
    val deadline = LocalDateTime.parse(roomInfo.deadline).format(uiFormatter)

    Column {
        Spacer(modifier = Modifier.height(MoimSpace.SpaceMedium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = roomInfo.description.toString())
            Column(horizontalAlignment = Alignment.End) {
                Text(text = deadline)
                Text(text = "${roomInfo.currentMemberCount} / ${roomInfo.maxCount}")
            }
        }
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