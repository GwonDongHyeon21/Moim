package com.moim.backend.domain.vote.service

import com.moim.backend.core.error.ErrorCode
import com.moim.backend.core.error.ErrorException
import com.moim.backend.core.model.Category
import com.moim.backend.domain.room.repository.RoomRepository
import com.moim.backend.domain.user.repository.UserRepository
import com.moim.backend.domain.vote.dto.*
import com.moim.backend.domain.vote.entity.Candidate
import com.moim.backend.domain.vote.entity.VoteRecord
import com.moim.backend.domain.vote.repository.CandidateRepository
import com.moim.backend.domain.vote.repository.VoteRecordRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class VoteService(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository,
    private val candidateRepository: CandidateRepository,
    private val voteRecordRepository: VoteRecordRepository
) {
    fun getCategories(): List<CategoryResponse> {
        return Category.entries.map { CategoryResponse.from(it) }
    }

    @Transactional
    fun createCandidate(userId: Long, roomId: Long, request: CreateCandidateRequest) {
        val user = userRepository.findById(userId).orElseThrow {
            ErrorException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND)
        }

        val room = roomRepository.findById(roomId).orElseThrow {
            ErrorException(HttpStatus.NOT_FOUND, ErrorCode.ROOM_NOT_FOUND)
        }

        if (LocalDateTime.now().isAfter(room.deadline)) {
            throw ErrorException(HttpStatus.FORBIDDEN, ErrorCode.ROOM_DEADLINE_EXPIRED)
        }

        val currentCount = candidateRepository.countByRoomIdAndCategoryAndUserId(
            roomId = roomId,
            category = request.category,
            userId = userId
        )
        if (currentCount >= 3) {
            throw ErrorException(HttpStatus.BAD_REQUEST, ErrorCode.CANDIDATE_COUNT_LIMIT)
        }

        candidateRepository.save(
            Candidate(
                room = room,
                category = request.category,
                user = user,
                content = request.content
            )
        )
    }

    fun getCandidates(userId: Long, roomId: Long, category: Category): List<CandidateResponse> {
        val candidates = candidateRepository.findAllByRoomIdAndCategory(roomId, category)

        val myVotedCandidateIds = voteRecordRepository.findAllByUserIdAndRoomId(userId, roomId)
            .map { it.candidate.id }

        return candidates.map {
            CandidateResponse(
                id = it.id!!,
                category = it.category,
                content = it.content,
                creatorNickname = it.user.nickname,
                isVotedByMe = myVotedCandidateIds.contains(it.id)
            )
        }
    }

    @Transactional
    fun castVote(userId: Long, candidateId: Long) {
        val user = userRepository.findById(userId).orElseThrow {
            ErrorException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND)
        }

        val candidate = candidateRepository.findById(candidateId).orElseThrow {
            ErrorException(HttpStatus.NOT_FOUND, ErrorCode.CANDIDATE_NOT_FOUND)
        }

        val isExistingRecord = voteRecordRepository.findByUserIdAndCandidateId(userId, candidateId)
        if (isExistingRecord != null) {
            voteRecordRepository.delete(isExistingRecord)
        } else {
            voteRecordRepository.save(
                VoteRecord(
                    candidate = candidate,
                    user = user
                )
            )
        }
    }

    @Transactional
    fun resetVotes(userId: Long, roomId: Long, category: Category) {
        val candidateIds = candidateRepository.findAllByRoomIdAndCategory(roomId, category).map { it.id!! }

        if (candidateIds.isNotEmpty()) {
            voteRecordRepository.deleteByUserIdAndCandidateIdIn(userId, candidateIds)
        }
    }

    fun getVoteResults(roomId: Long): List<VoteResultResponse> {
        val room = roomRepository.findById(roomId).orElseThrow {
            ErrorException(HttpStatus.NOT_FOUND, ErrorCode.ROOM_NOT_FOUND)
        }

        if (LocalDateTime.now().isBefore(room.deadline)) {
            throw ErrorException(HttpStatus.FORBIDDEN, ErrorCode.VOTE_RESULTS_BLINDED)
        }

        val groupedCandidates = candidateRepository.findAllByRoomId(roomId).groupBy { it.category }

        return Category.entries.map { category ->
            val candidatesInCategory = groupedCandidates[category] ?: emptyList()
            val rankings = candidatesInCategory.map { candidate ->
                VoteRankDto(
                    content = candidate.content,
                    voteCount = voteRecordRepository.countByCandidateId(candidate.id!!)
                )
            }

            VoteResultResponse(
                category = category,
                rankings = rankings
            )
        }
    }
}