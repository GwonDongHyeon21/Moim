package com.moim.presentation.util

import com.moim.presentation.model.RoomInfoUiModel
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiModel
import com.moim.presentation.screen.roomdetail.model.RoomMemberInfoUiModel

object DummyData {

    val dummyRooms = listOf(
        RoomInfoUiModel(
            code = "1",
            title = "asdf",
            description = "asdfasdf",
            maxCount = 10,
            currentMemberCount = 4
        ),
        RoomInfoUiModel(
            code = "2",
            title = "qwer",
            description = "qwerqwer",
            maxCount = 10,
            currentMemberCount = 3
        ),
        RoomInfoUiModel(
            code = "3",
            title = "zxcv",
            description = "zxcvzxcv",
            maxCount = 10,
            currentMemberCount = 10
        )
    )

    val dummyMembers = listOf(
        RoomMemberInfoUiModel(
            userId = 1,
            nickname = "nickname1",
            profileImageUrl = null,
            role = "HOST"
        ),
        RoomMemberInfoUiModel(
            userId = 2,
            nickname = "nickname2",
            profileImageUrl = null,
            role = "MEMBER"
        )
    )

    val dummyRoomDetail = RoomDetailUiModel(
        roomInfo = dummyRooms.first(),
        role = "HOST",
        members = dummyMembers,
    )
}