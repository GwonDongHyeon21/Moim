package com.moim.data.feature.vote.datasource

import com.moim.data.common.model.ApiResponse
import com.moim.data.feature.vote.model.CandidateResponse
import com.moim.data.feature.vote.model.CreateCandidateRequest
import com.moim.data.feature.vote.model.VoteResultResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VoteService {

    @POST("/api/v1/votes/rooms/{roomId}/candidates")
    suspend fun createCandidate(
        @Path("roomId") roomId: Long,
        @Body request: CreateCandidateRequest
    ): ApiResponse<Boolean>

    @GET("/api/v1/votes/rooms/{roomId}/candidates")
    suspend fun getCandidates(
        @Path("roomId") roomId: Long,
        @Query("category") category: String
    ): ApiResponse<List<CandidateResponse>>

    @POST("/api/v1/votes/candidates/{candidateId}/vote")
    suspend fun castVote(
        @Path("candidateId") candidateId: Long
    ): ApiResponse<Boolean>

    @DELETE("/api/v1/votes/rooms/{roomId}/reset")
    suspend fun resetVotes(
        @Path("roomId") roomId: Long,
        @Query("category") category: String
    ): ApiResponse<Boolean>

    @GET("/api/v1/votes/rooms/{roomId}/results")
    suspend fun getVoteResults(
        @Path("roomId") roomId: Long
    ): ApiResponse<List<VoteResultResponse>>
}