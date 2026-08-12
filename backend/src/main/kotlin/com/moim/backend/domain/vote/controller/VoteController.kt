package com.moim.backend.domain.vote.controller

import com.moim.backend.core.model.Category
import com.moim.backend.core.response.ApiResponse
import com.moim.backend.domain.vote.dto.*
import com.moim.backend.domain.vote.service.VoteService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/votes")
class VoteController(
    private val voteService: VoteService
) {

    @GetMapping("/categories")
    fun getCategories(): ApiResponse<List<CategoryResponse>> {
        val categories = voteService.getCategories()

        return ApiResponse.success(categories)
    }

    @PostMapping("/{roomId}/candidates")
    fun createCandidate(
        @AuthenticationPrincipal userId: Long,
        @PathVariable roomId: Long,
        @RequestBody request: CreateCandidateRequest
    ): ApiResponse<Boolean> {
        voteService.createCandidate(userId, roomId, request)

        return ApiResponse.success(true)
    }

    @PutMapping("/{roomId}/candidates")
    fun updateCandidate(
        @AuthenticationPrincipal userId: Long,
        @PathVariable roomId: Long,
        @RequestBody request: List<UpdateCandidateRequest>
    ): ApiResponse<Boolean> {
        voteService.updateCandidates(userId, roomId, request)

        return ApiResponse.success(true)
    }

    @GetMapping("/{roomId}/candidates")
    fun getCandidates(
        @AuthenticationPrincipal userId: Long,
        @PathVariable roomId: Long,
        @RequestParam category: Category
    ): ApiResponse<List<CandidateResponse>> {
        val response = voteService.getCandidates(userId, roomId, category)

        return ApiResponse.success(response)
    }

    @PostMapping("/{candidateId}/vote")
    fun castVote(
        @AuthenticationPrincipal userId: Long,
        @PathVariable candidateId: Long
    ): ApiResponse<Boolean> {
        voteService.castVote(userId, candidateId)

        return ApiResponse.success(true)
    }

    @DeleteMapping("/{roomId}/reset")
    fun resetVotes(
        @AuthenticationPrincipal userId: Long,
        @PathVariable roomId: Long,
        @RequestParam category: Category
    ): ApiResponse<Boolean> {
        voteService.resetVotes(userId, roomId, category)

        return ApiResponse.success(true)
    }

    @GetMapping("/{roomId}/candidates/me")
    fun getMyCandidates(
        @AuthenticationPrincipal userId: Long,
        @PathVariable roomId: Long,
        @RequestParam category: Category
    ): ApiResponse<List<CandidateResponse>> {
        val response = voteService.getMyCandidatesByCategory(userId, roomId, category)

        return ApiResponse.success(response)
    }

    @GetMapping("/{roomId}/results")
    fun getVoteResults(
        @PathVariable roomId: Long
    ): ApiResponse<List<VoteResultResponse>> {
        val response = voteService.getVoteResults(roomId)

        return ApiResponse.success(response)
    }
}