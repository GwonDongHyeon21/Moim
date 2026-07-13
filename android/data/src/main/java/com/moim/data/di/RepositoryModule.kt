package com.moim.data.di

import com.moim.data.feature.room.repositoryimpl.RoomRepositoryImpl
import com.moim.data.feature.user.repositoryimpl.UserRepositoryImpl
import com.moim.domain.repository.RoomRepository
import com.moim.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        authRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindRoomRepository(
        romRepositoryImpl: RoomRepositoryImpl
    ): RoomRepository
}