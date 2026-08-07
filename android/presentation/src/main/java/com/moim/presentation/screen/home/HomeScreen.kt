package com.moim.presentation.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import com.moim.presentation.screen.component.dialog.CreateUpdateRoomDialog
import com.moim.presentation.screen.component.dialog.MoimBasicDialog
import com.moim.presentation.screen.home.HomeScreen.ANIMATION_DURATION_MILLIS
import com.moim.presentation.screen.home.component.JoinRoomDialog
import com.moim.presentation.screen.home.component.RoomCard
import com.moim.presentation.screen.home.component.RoomFilterTab
import com.moim.presentation.screen.home.component.RoomFloatingActionButton
import com.moim.presentation.screen.home.model.HomeAction
import com.moim.presentation.screen.home.model.HomeEvent
import com.moim.presentation.screen.home.model.HomeUiState
import com.moim.presentation.screen.home.model.RoomFilterStatus
import com.moim.presentation.screen.home.model.RoomOptions.CREATE
import com.moim.presentation.screen.home.model.RoomOptions.DELETE
import com.moim.presentation.screen.home.model.RoomOptions.JOIN
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.util.DummyData
import com.moim.presentation.util.collectWithLifecycle
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

private object HomeScreen {
    const val ANIMATION_DURATION_MILLIS = 400
}

@Composable
fun HomeScreen(
    onNavigateToRoomDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val ongoingRoomsPagingItems = viewModel.ongoingRoomsPagingItems.collectAsLazyPagingItems()
    val closedRoomsPagingItems = viewModel.closedRoomsPagingItems.collectAsLazyPagingItems()

    val pagerState = rememberPagerState(pageCount = { RoomFilterStatus.entries.size })

    val pagingItemsList = listOf(ongoingRoomsPagingItems, closedRoomsPagingItems)
    val roomsPagingItems = pagingItemsList[pagerState.currentPage]

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {
            is HomeEvent.NavigateToRoomDetail -> {
                onNavigateToRoomDetail(event.roomId)
                ongoingRoomsPagingItems.refresh()
            }

            is HomeEvent.RefreshRoom -> {
                roomsPagingItems.refresh()
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val status = RoomFilterStatus.entries[pagerState.currentPage]
        viewModel.onAction(HomeAction.OnRoomFilterStatusSelected(status))
    }

    LaunchedEffect(roomsPagingItems.loadState.refresh) {
        if (roomsPagingItems.loadState.refresh !is LoadState.Loading) {
            viewModel.onAction(HomeAction.OnRefreshing(false))
        }
    }

    HomeScreen(
        uiState = uiState,
        pagingItemsList = pagingItemsList,
        roomsPagingItems = roomsPagingItems,
        pagerState = pagerState,
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
    pagingItemsList: List<LazyPagingItems<RoomInfoUiModel>>,
    roomsPagingItems: LazyPagingItems<RoomInfoUiModel>,
    pagerState: PagerState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

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
                    CREATE -> onAction(HomeAction.ClickDialog(true, CREATE.name))
                    JOIN -> onAction(HomeAction.ClickDialog(true, JOIN.name))
                    DELETE -> Unit
                }
            }
        },
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = {
                onAction(HomeAction.OnRefreshing(true))
                roomsPagingItems.refresh()
            },
            state = pullToRefreshState,
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                RoomFilterTab(
                    selectedStatus = uiState.roomFilterStatus,
                    onStatusSelected = { status ->
                        onAction(HomeAction.OnRoomFilterStatusSelected(status))
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                page = status.ordinal,
                                animationSpec = tween(durationMillis = ANIMATION_DURATION_MILLIS)
                            )
                        }
                    }
                )

                HorizontalPager(state = pagerState) { page ->
                    MoimPagingList(
                        pagingItems = pagingItemsList[page],
                        itemKey = { it.code },
                        emptyContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = stringResource(R.string.empty_rooms))
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = MoimPadding.AppHorizontalPadding),
                        contentPadding = PaddingValues(bottom = MoimPadding.PaddingSmall)
                    ) { room ->
                        Spacer(modifier = Modifier.height(MoimSpace.SpaceSmall))
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
            CREATE.name -> {
                CreateUpdateRoomDialog(
                    title = uiState.title,
                    description = uiState.description,
                    selectedDateTime = uiState.selectedDateTime,
                    onConfirmValue = stringResource(R.string.add),
                    onTitleChanged = { onAction(HomeAction.OnTitleChanged(it)) },
                    onDescriptionChanged = { onAction(HomeAction.OnDescriptionChanged(it)) },
                    onDateTimeSelected = { onAction(HomeAction.OnDateTimeSelected(it)) },
                    onConfirm = { onAction(HomeAction.CreateRoom(it)) },
                    onDismissRequest = { onAction(HomeAction.ClickDialog(false, "")) },
                    enabled = uiState.title.isNotBlank() && uiState.selectedDateTime != null,
                )
            }

            JOIN.name -> {
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
    val pagerState = rememberPagerState(pageCount = { RoomFilterStatus.entries.size })

    HomeScreen(
        uiState = HomeUiState(),
        roomsPagingItems = dummyPagingItems,
        pagingItemsList = emptyList(),
        pagerState = pagerState,
        onAction = {}
    )
}