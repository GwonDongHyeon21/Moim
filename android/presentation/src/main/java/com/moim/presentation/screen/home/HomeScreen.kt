package com.moim.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moim.presentation.R
import com.moim.presentation.model.RoomInfoUiModel
import com.moim.presentation.screen.component.MoimPagingList
import com.moim.presentation.screen.component.MoimProgressIndicator
import com.moim.presentation.screen.component.MoimTopBar
import com.moim.presentation.screen.home.component.CreateRoomDialog
import com.moim.presentation.screen.home.component.JoinRoomDialog
import com.moim.presentation.screen.home.component.RoomCard
import com.moim.presentation.screen.home.component.RoomFilterTab
import com.moim.presentation.screen.home.component.RoomFloatingActionButton
import com.moim.presentation.screen.home.model.HomeAction
import com.moim.presentation.screen.home.model.HomeEvent
import com.moim.presentation.screen.home.model.HomeUiState
import com.moim.presentation.screen.home.model.RoomOptions
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.util.DummyData
import com.moim.presentation.util.collectWithLifecycle
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    onNavigateToRoomDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val roomsPagingItems = viewModel.roomsPagingItems.collectAsLazyPagingItems()

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {
            is HomeEvent.NavigateToRoomDetail -> {
                onNavigateToRoomDetail(event.roomId)
                roomsPagingItems.refresh()
            }

            is HomeEvent.RefreshRoom -> {
                roomsPagingItems.refresh()
            }
        }
    }

    HomeScreen(
        uiState = uiState,
        roomsPagingItems = roomsPagingItems,
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
    roomsPagingItems: LazyPagingItems<RoomInfoUiModel>,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(roomsPagingItems.loadState.refresh) {
        if (roomsPagingItems.loadState.refresh !is LoadState.Loading) {
            isRefreshing = false
        }
    }

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
                    RoomOptions.CREATE ->
                        onAction(HomeAction.ClickDialog(true, RoomOptions.CREATE.name))

                    RoomOptions.JOIN ->
                        onAction(HomeAction.ClickDialog(true, RoomOptions.JOIN.name))
                }
            }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                roomsPagingItems.refresh()
            },
            state = pullToRefreshState,
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                RoomFilterTab(
                    selectedStatus = uiState.roomFilterStatus,
                    onStatusSelected = { onAction(HomeAction.OnRoomFilterStatusSelected(it)) }
                )

                MoimPagingList(
                    pagingItems = roomsPagingItems,
                    itemKey = { it.code },
                    emptyContent = {
                        Text(
                            text = stringResource(R.string.empty_rooms),
                            modifier = Modifier.fillMaxSize()
                        )
                    },
                    modifier = Modifier.padding(horizontal = MoimPadding.AppHorizontalPadding)
                ) { room ->
                    Spacer(modifier = Modifier.height(MoimSpace.SpaceSmall))
                    RoomCard(
                        room = room,
                        onClick = { onAction(HomeAction.ClickRoom(room.id!!)) }
                    )
                }
                Spacer(modifier = Modifier.height(MoimSpace.SpaceSmall))
            }
        }
    }

    if (uiState.isExpanded) {
        when (uiState.roomOption) {
            RoomOptions.CREATE.name -> {
                CreateRoomDialog(
                    title = uiState.title,
                    description = uiState.description,
                    selectedDateTime = uiState.selectedDateTime,
                    onTitleChanged = { onAction(HomeAction.OnTitleChanged(it)) },
                    onDescriptionChanged = { onAction(HomeAction.OnDescriptionChanged(it)) },
                    onDateTimeSelected = { onAction(HomeAction.OnDateTimeSelected(it)) },
                    onConfirm = { onAction(HomeAction.CreateRoom(it)) },
                    onDismissRequest = { onAction(HomeAction.ClickDialog(false, "")) }
                )
            }

            RoomOptions.JOIN.name -> {
                JoinRoomDialog(
                    onConfirm = { onAction(HomeAction.JoinRoom(it)) },
                    onDismissRequest = { onAction(HomeAction.ClickDialog(false, "")) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val dummyPagingFlow = flowOf(PagingData.from(DummyData.dummyRooms))
    val dummyPagingItems = dummyPagingFlow.collectAsLazyPagingItems()

    HomeScreen(
        uiState = HomeUiState(),
        roomsPagingItems = dummyPagingItems,
        onAction = {}
    )
}