package com.moim.backend.domain.vote.dto

import com.moim.backend.domain.vote.entity.Candidate

data class VoteRankDto(
    val candidate: Candidate,
    val voteCount: Int
)