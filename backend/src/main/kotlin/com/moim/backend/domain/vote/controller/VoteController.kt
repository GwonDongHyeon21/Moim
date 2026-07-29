package com.moim.backend.domain.vote.controller

import com.moim.backend.core.model.Category
import com.moim.backend.core.response.ApiResponse
import com.moim.backend.domain.vote.dto.CandidateResponse
import com.moim.backend.domain.vote.dto.CreateCandidateRequest
import com.moim.backend.domain.vote.dto.VoteResultResponse
import com.moim.backend.domain.vote.service.VoteService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/votes")
class VoteController(
    private val voteService: VoteService
) {

    @PostMapping("/rooms/{roomId}/candidates")
    fun createCandidate(
        @AuthenticationPrincipal userId: Long,
        @PathVariable roomId: Long,
        @RequestBody request: CreateCandidateRequest
    ): ApiResponse<Boolean> {
        voteService.createCandidate(userId, roomId, request)

        return ApiResponse.success(true)
    }

    @GetMapping("/rooms/{roomId}/candidates")
    fun getCandidates(
        @AuthenticationPrincipal userId: Long,
        @PathVariable roomId: Long,
        @RequestParam category: Category
    ): ApiResponse<List<CandidateResponse>> {
        val response = voteService.getCandidates(userId, roomId, category)

        return ApiResponse.success(response)
    }

    @PostMapping("/candidates/{candidateId}/vote")
    fun castVote(
        @AuthenticationPrincipal userId: Long,
        @PathVariable candidateId: Long
    ): ApiResponse<Boolean> {
        voteService.castVote(userId, candidateId)

        return ApiResponse.success(true)
    }

    @DeleteMapping("/rooms/{roomId}/reset")
    fun resetVotes(
        @AuthenticationPrincipal userId: Long,
        @PathVariable roomId: Long,
        @RequestParam category: Category
    ): ApiResponse<Boolean> {
        voteService.resetVotes(userId, roomId, category)

        return ApiResponse.success(true)
    }

    @GetMapping("/rooms/{roomId}/results")
    fun getVoteResults(
        @PathVariable roomId: Long
    ): ApiResponse<List<VoteResultResponse>> {
        val response = voteService.getVoteResults(roomId)

        return ApiResponse.success(response)
    }
}