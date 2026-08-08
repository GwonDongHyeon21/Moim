package com.moim.presentation.screen.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.util.DummyData
import kotlinx.coroutines.flow.flowOf

@Composable
fun <T : Any> MoimPagingList(
    pagingItems: LazyPagingItems<T>,
    itemKey: (T) -> Any,
    emptyContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues.Zero,
    itemContent: @Composable (T) -> Unit
) {
    val loadState = pagingItems.loadState
    val isRefreshing =
        loadState.mediator?.refresh is LoadState.Loading || loadState.refresh is LoadState.Loading
    val errorState =
        loadState.mediator?.refresh as? LoadState.Error ?: loadState.refresh as? LoadState.Error

    if (pagingItems.itemCount > 0) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding
        ) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { itemKey(it) }
            ) { index ->
                pagingItems[index]?.let { itemContent(it) }
            }

            handleAppendState(loadState.append, pagingItems::retry)
        }

        return
    }

    when {
        isRefreshing -> {
            MoimProgressIndicator()
        }

        errorState != null -> {
            ErrorScreen(
                onRetry = { pagingItems.retry() },
                modifier = Modifier.fillMaxSize(),
                message = errorState.error.localizedMessage
            )
        }

        loadState.append.endOfPaginationReached && pagingItems.itemCount == 0 -> {
            emptyContent()
        }
    }
}

fun LazyListScope.handleAppendState(
    appendState: LoadState,
    onRetry: () -> Unit
) {
    when (appendState) {
        is LoadState.Loading -> {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MoimPadding.PaddingSmall)
                )
            }
        }

        is LoadState.Error -> {
            item {
                ErrorScreen(
                    onRetry = onRetry,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MoimPadding.PaddingSmall),
                    message = appendState.error.message
                )
            }
        }

        is LoadState.NotLoading -> {}
    }
}

@Preview(showBackground = true)
@Composable
private fun MoimPagingListPreview() {
    val dummyPagingFlow = flowOf(PagingData.from(DummyData.dummyRooms))
    val dummyPagingItems = dummyPagingFlow.collectAsLazyPagingItems()

    MoimPagingList(
        pagingItems = dummyPagingItems,
        itemKey = { it.code },
        emptyContent = {},
        modifier = Modifier.fillMaxSize(),
        itemContent = { Text(text = it.title) }
    )
}