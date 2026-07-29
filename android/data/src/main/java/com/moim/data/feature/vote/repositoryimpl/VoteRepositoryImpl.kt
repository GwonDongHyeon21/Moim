package com.moim.data.feature.vote.repositoryimpl

import com.moim.data.feature.vote.datasource.VoteDataSource
import com.moim.data.feature.vote.model.toDomain
import com.moim.domain.feature.vote.model.CandidateInfo
import com.moim.domain.feature.vote.model.CategoryInfo
import com.moim.domain.feature.vote.model.VoteResultInfo
import com.moim.domain.feature.vote.repository.VoteRepository
import javax.inject.Inject

class VoteRepositoryImpl @Inject constructor(
    private val voteDataSource: VoteDataSource
) : VoteRepository {

    override suspend fun getCategories(): Result<List<CategoryInfo>> {
        return voteDataSource.getCategories()
            .map { it.map { category -> category.toDomain() } }
    }

    override suspend fun createCandidate(
        roomId: Long,
        category: String,
        content: String
    ): Result<Boolean> {
        return voteDataSource.createCandidate(roomId, category, content)
    }

    override suspend fun getCandidates(
        roomId: Long,
        category: String
    ): Result<List<CandidateInfo>> {
        return voteDataSource.getCandidates(roomId, category)
            .map { it.map { candidate -> candidate.toDomain() } }
    }

    override suspend fun castVote(candidateId: Long): Result<Boolean> {
        return voteDataSource.castVote(candidateId)
    }

    override suspend fun resetVotes(roomId: Long, category: String): Result<Boolean> {
        return voteDataSource.resetVotes(roomId, category)
    }

    override suspend fun getVoteResults(roomId: Long): Result<List<VoteResultInfo>> {
        return voteDataSource.getVoteResults(roomId)
            .map { it.map { result -> result.toDomain() } }
    }
}