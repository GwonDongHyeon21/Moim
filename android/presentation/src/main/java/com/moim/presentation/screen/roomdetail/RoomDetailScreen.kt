package com.moim.presentation.screen.roomdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.navigation3.runtime.result.ResultEffect
import com.moim.presentation.R
import com.moim.presentation.model.RoomInfoUiModel
import com.moim.presentation.navigation.RoomDetail
import com.moim.presentation.screen.component.MoimProgressIndicator
import com.moim.presentation.screen.component.dialog.CreateUpdateRoomDialog
import com.moim.presentation.screen.roomdetail.component.CategoryCard
import com.moim.presentation.screen.roomdetail.component.RoomCodeDialog
import com.moim.presentation.screen.roomdetail.component.RoomDetailTopBar
import com.moim.presentation.screen.roomdetail.model.RoomDetailAction
import com.moim.presentation.screen.roomdetail.model.RoomDetailEvent
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiModel
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiState
import com.moim.presentation.screen.vote.VoteResult
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
    onNavigateBackRefresh: () -> Unit,
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

            RoomDetailEvent.NavigateBackRefresh -> onNavigateBackRefresh()
        }
    }

    ResultEffect<VoteResult> { result ->
        if (result.shouldRefresh) {
            viewModel.onAction(RoomDetailAction.LoadRoomDetail)
        }
    }

    if (uiState.roomDetail.roomInfo.id == null) {
        MoimProgressIndicator()
    } else {
        RoomDetailScreen(
            uiState = uiState,
            onAction = viewModel::onAction,
            modifier = modifier
        )
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
    val deadline = LocalDateTime.parse(roomInfo.deadline)

    val isoFormatter =
        DateTimeFormatter.ofPattern(stringResource(R.string.iso_time_format), Locale.KOREA)

    val editEnable = !(roomInfo.title == uiState.title
            && roomInfo.description == uiState.description
            && roomInfo.maxCount == uiState.maxCount
            && roomInfo.deadline == uiState.selectedDateTime.format(isoFormatter))

    val pullToRefreshState = rememberPullToRefreshState()

    var showRoomCode by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            RoomDetailTopBar(
                value = roomInfo.title,
                editEnabled = roomInfo.isHost && deadline > LocalDateTime.now(),
                onClickRoomCode = { showRoomCode = true },
                onClickUpdate = { showUpdateDialog = true },
                onClickDelete = { onAction(RoomDetailAction.DeleteRoom) },
                onClickNavigationIcon = { onAction(RoomDetailAction.NavigateBack) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(RoomDetailAction.NavigateToCandidateCreate) }) {
                Icon(
                    painter = painterResource(R.drawable.add_24),
                    contentDescription = null
                )
            }
        },
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->
        if (deadline > LocalDateTime.now()) {
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = { onAction(RoomDetailAction.RefreshRoomDetail) },
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
                    RoomDetailInfoSection(
                        roomInfo = roomInfo,
                        deadline = deadline
                    )

                    categoryVoteStatus.forEach { category ->
                        CategoryCard(category = category) {
                            onAction(RoomDetailAction.NavigateToVote(category.category))
                        }
                    }
                }
            }
        } else {
            RoomDetailResultScreen(
                currentMemberCount = roomInfo.currentMemberCount,
                voteResult = uiState.voteResult,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }

    if (showRoomCode) {
        RoomCodeDialog(
            roomCode = roomInfo.code,
            onDismissRequest = { showRoomCode = false }
        )
    }

    if (showUpdateDialog) {
        CreateUpdateRoomDialog(
            title = uiState.title,
            description = uiState.description,
            selectedDateTime = uiState.selectedDateTime,
            onConfirmValue = stringResource(R.string.room_detail_update),
            onTitleChanged = { onAction(RoomDetailAction.OnTitleChanged(it)) },
            onDescriptionChanged = { onAction(RoomDetailAction.OnDescriptionChanged(it)) },
            onDateTimeSelected = { onAction(RoomDetailAction.OnDateTimeSelected(it)) },
            onConfirm = { onAction(RoomDetailAction.UpdateRoom(it)) },
            onDismissRequest = { showUpdateDialog = false },
            enabled = editEnable
        )
    }
}

@Composable
fun RoomDetailInfoSection(
    roomInfo: RoomInfoUiModel,
    deadline: LocalDateTime
) {
    val uiFormatter =
        DateTimeFormatter.ofPattern(stringResource(R.string.ui_time_format), Locale.KOREA)

    Column {
        Spacer(modifier = Modifier.height(MoimSpace.SpaceMedium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = roomInfo.description.toString())
            Column(horizontalAlignment = Alignment.End) {
                Text(text = deadline.format(uiFormatter))
                Text(text = "${roomInfo.currentMemberCount} / ${roomInfo.maxCount}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomDetailScreenPreview() {
    RoomDetailScreen(
        uiState = RoomDetailUiState(
            roomDetail = RoomDetailUiModel(
                roomInfo = DummyData.dummyRooms[4],
                categoryVoteStatus = DummyData.dummyCategoryVoteStatus
            )
        ),
        onAction = {}
    )
}

@Preview(showBackground = true)
@Composable
fun RoomDetailScreenPreview2() {
    RoomDetailScreen(
        uiState = RoomDetailUiState(
            roomDetail = DummyData.dummyRoomDetail,
            voteResult = DummyData.dummyVoteResults
        ),
        onAction = {}
    )
}