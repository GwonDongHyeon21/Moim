package com.moim.backend.domain.vote.dto

import com.moim.backend.core.model.Category
import com.moim.backend.domain.vote.entity.Candidate

data class CandidateResponse(
    val id: Long,
    val category: Category,
    val content: String,
    val creatorNickname: String,
    val isVotedByMe: Boolean
) {
    companion object {
        fun from(candidate: Candidate, isVotedByMe: Boolean): CandidateResponse {
            return CandidateResponse(
                id = candidate.id!!,
                category = candidate.category,
                content = candidate.content,
                creatorNickname = candidate.user.nickname,
                isVotedByMe = isVotedByMe
            )
        }
    }
}