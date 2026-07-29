package com.moim.data.di

import com.moim.data.feature.room.datasource.RoomDataSource
import com.moim.data.feature.room.datasource.RoomDataSourceImpl
import com.moim.data.feature.user.datasource.UserDataSource
import com.moim.data.feature.user.datasource.UserDataSourceImpl
import com.moim.data.feature.vote.datasource.VoteDataSource
import com.moim.data.feature.vote.datasource.VoteDataSourceImpl
import com.moim.data.feature.vote.repositoryimpl.VoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        userDataSourceImpl: UserDataSourceImpl
    ): UserDataSource

    @Binds
    @Singleton
    abstract fun bindRoomRemoteDataSource(
        roomDataSourceImpl: RoomDataSourceImpl
    ): RoomDataSource

    @Binds
    @Singleton
    abstract fun bindVoteRemoteDataSource(
        voteDataSourceImpl: VoteDataSourceImpl
    ): VoteDataSource
}