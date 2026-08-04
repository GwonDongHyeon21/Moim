package com.moim.data.feature.room.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.moim.data.common.paging.BasePagingSource
import com.moim.data.feature.room.datasource.RoomDataSource
import com.moim.data.feature.room.model.CreateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.toDomain
import com.moim.domain.feature.room.model.CreateRoomParams
import com.moim.domain.feature.room.model.RoomDetailInfo
import com.moim.domain.feature.room.model.RoomInfo
import com.moim.domain.feature.room.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource
) : RoomRepository {

    override fun getRoomsPaging(status: String): Flow<PagingData<RoomInfo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                BasePagingSource(
                    pageSize = PAGE_SIZE,
                    apiCall = { page, size ->
                        roomDataSource.getRoomsPaging(page, size, status)
                    },
                    getError = { result ->
                        result.exceptionOrNull() as? Exception
                    },
                    getData = { result ->
                        result.getOrNull() ?: emptyList()
                    },
                    getHasNext = { result ->
                        val data = result.getOrNull()
                        data != null && data.size == PAGE_SIZE
                    }
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun loadRoomDetail(roomId: Long): Result<RoomDetailInfo> {
        return roomDataSource.loadRoomDetail(roomId)
            .map { it.toDomain() }
    }

    override suspend fun createRoom(roomInfo: CreateRoomParams): Result<RoomInfo> {
        return roomDataSource.createRoom(
            CreateRoomRequest(
                title = roomInfo.title,
                description = roomInfo.description,
                maxCount = roomInfo.maxCount,
                deadline = roomInfo.deadline
            )
        ).map { it.toDomain() }
    }

    override suspend fun joinRoom(roomCode: String): Result<RoomInfo> {
        return roomDataSource.joinRoom(JoinRoomRequest(roomCode))
            .map { it.toDomain() }
    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}