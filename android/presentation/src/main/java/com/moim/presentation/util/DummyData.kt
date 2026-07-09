package com.moim.presentation.util

import com.moim.domain.model.RoomInfo

object DummyData {
    val dummyRooms = listOf(
        RoomInfo(
            code = "1",
            title = "asdf",
            description = "asdfasdf",
            maxCount = 10,
            currentMemberCount = 4
        ),
        RoomInfo(
            code = "2",
            title = "qwer",
            description = "qwerqwer",
            maxCount = 10,
            currentMemberCount = 3
        ),
        RoomInfo(
            code = "3",
            title = "zxcv",
            description = "zxcvzxcv",
            maxCount = 10,
            currentMemberCount = 10
        )
    )
}