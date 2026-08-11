package com.moim.data.feature.vote.datasource

import com.moim.data.common.network.apiCall
import com.moim.data.feature.vote.model.CandidateResponse
import com.moim.data.feature.vote.model.CategoryResponse
import com.moim.data.feature.vote.model.CreateCandidateRequest
import com.moim.data.feature.vote.model.VoteResultResponse
import javax.inject.Inject

class VoteDataSourceImpl @Inject constructor(
    private val voteService: VoteService
) : VoteDataSource {

    override suspend fun getCategories(): Result<List<CategoryResponse>> {
        return apiCall { voteService.getCategories() }
    }

    override suspend fun createCandidate(
        roomId: Long,
        category: String,
        content: String
    ): Result<Boolean> {
        return apiCall {
            voteService.createCandidate(roomId, CreateCandidateRequest(category, content))
        }
    }

    override suspend fun getCandidates(
        roomId: Long,
        category: String
    ): Result<List<CandidateResponse>> {
        return apiCall { voteService.getCandidates(roomId, category) }
    }

    override suspend fun castVote(candidateId: Long): Result<Boolean> {
        return apiCall { voteService.castVote(candidateId) }
    }

    override suspend fun resetVotes(
        roomId: Long,
        category: String
    ): Result<Boolean> {
        return apiCall { voteService.resetVotes(roomId, category) }
    }

    override suspend fun getMyCandidates(
        roomId: Long,
        category: String
    ): Result<List<CandidateResponse>> {
        return apiCall { voteService.getMyCandidates(roomId, category) }
    }

    override suspend fun getVoteResults(roomId: Long): Result<List<VoteResultResponse>> {
        return apiCall { voteService.getVoteResults(roomId) }
    }

}