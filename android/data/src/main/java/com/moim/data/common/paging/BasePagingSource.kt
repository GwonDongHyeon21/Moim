package com.moim.data.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BasePagingSource<T : Any, R>(
    private val pageSize: Int,
    private val apiCall: suspend (page: Int, size: Int) -> R,
    private val getError: (R) -> Exception?,
    private val getData: (R) -> List<T>,
    private val getHasNext: (R) -> Boolean
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 0

        return try {
            val response = apiCall(page, pageSize)

            val error = getError(response)
            if (error != null) {
                return LoadResult.Error(error)
            }

            val items = getData(response)
            val hasNext = getHasNext(response)

            LoadResult.Page(
                data = items,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (hasNext) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}