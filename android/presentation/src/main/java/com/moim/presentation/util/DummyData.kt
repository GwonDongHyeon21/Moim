package com.moim.presentation.util

import com.moim.presentation.model.RoomInfoUiModel
import com.moim.presentation.screen.roomdetail.model.CategoryVoteStatusUiModel
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiModel
import com.moim.presentation.screen.roomdetail.model.RoomMemberInfoUiModel
import com.moim.presentation.screen.vote.model.CandidateUiModel

object DummyData {

    val dummyRooms = listOf(
        RoomInfoUiModel(
            code = "1",
            title = "테스트1",
            description = "테스트 description1",
            maxCount = 10,
            currentMemberCount = 4,
            deadline = "2026-05-15T14:30:00"
        ),
        RoomInfoUiModel(
            code = "2",
            title = "테스트2",
            description = "테스트 description2",
            maxCount = 10,
            currentMemberCount = 3,
            deadline = "2026-07-20T09:15:22"
        ),
        RoomInfoUiModel(
            code = "3",
            title = "테스트3",
            description = "테스트 description3",
            maxCount = 100,
            currentMemberCount = 10,
            deadline = "2026-08-01T18:45:50"
        ),
        RoomInfoUiModel(
            code = "4",
            title = "테스트4",
            description = "테스트 description4",
            maxCount = 10,
            currentMemberCount = 10,
            deadline = "2026-08-01T18:45:50"
        ),
        RoomInfoUiModel(
            code = "5",
            title = "테스트5",
            description = "테스트 description5",
            maxCount = 15,
            currentMemberCount = 8,
            deadline = "2026-08-01T18:45:50"
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

    val dummyCategoryVoteStatus = listOf(
        CategoryVoteStatusUiModel(
            category = "FOOD",
            isVoted = true
        ),
        CategoryVoteStatusUiModel(
            category = "CAFE",
            isVoted = true
        ),
        CategoryVoteStatusUiModel(
            category = "PLACE",
            isVoted = false
        ),
        CategoryVoteStatusUiModel(
            category = "ACTIVITY",
            isVoted = false
        )
    )

    val dummyRoomDetail = RoomDetailUiModel(
        roomInfo = dummyRooms.first(),
        role = "HOST",
        members = dummyMembers,
        categoryVoteStatus = dummyCategoryVoteStatus
    )

    val dummyCandidates = listOf(
        CandidateUiModel(
            id = 1,
            category = "FOOD",
            content = "삼겹살",
            creatorNickname = "nickname1",
            isVotedByMe = true
        ),
        CandidateUiModel(
            id = 2,
            category = "FOOD",
            content = "샤브샤브",
            creatorNickname = "nickname2",
            isVotedByMe = false
        ),
        CandidateUiModel(
            id = 3,
            category = "CAFE",
            content = "메가커피",
            creatorNickname = "nickname1",
            isVotedByMe = true
        ),
        CandidateUiModel(
            id = 4,
            category = "CAFE",
            content = "스타벅스",
            creatorNickname = "nickname3",
            isVotedByMe = false
        ),
        CandidateUiModel(
            id = 5,
            category = "PLACE",
            content = "남산타워",
            creatorNickname = "nickname1",
            isVotedByMe = false
        ),
        CandidateUiModel(
            id = 6,
            category = "ACTIVITY",
            content = "축구",
            creatorNickname = "nickname4",
            isVotedByMe = true
        ),
    )
}