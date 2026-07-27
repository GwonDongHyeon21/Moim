package com.moim.presentation.screen.vote

import com.moim.domain.feature.vote.repository.VoteRepository
import com.moim.presentation.base.BaseViewModel
import com.moim.presentation.navigation.Vote
import com.moim.presentation.screen.vote.model.VoteAction
import com.moim.presentation.screen.vote.model.VoteEvent
import com.moim.presentation.screen.vote.model.VoteUiState
import com.moim.presentation.screen.vote.model.toUiModel
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber

@HiltViewModel(assistedFactory = VoteViewModel.Factory::class)
class VoteViewModel @AssistedInject constructor(
    @Assisted route: Vote,
    private val voteRepository: VoteRepository,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<VoteUiState, VoteEvent>(VoteUiState()) {

    override fun checkLoading(): Boolean = uiState.value.isLoading
    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    private val roomId = route.roomId
    private val category = route.category

    init {
        loadCandidates()
    }

    fun onAction(action: VoteAction) {
        when (action) {
            is VoteAction.CastVote -> castVote(action.candidateId)

            is VoteAction.NavigateBack -> sendEvent(VoteEvent.NavigateBack)
        }
    }

    private fun loadCandidates() = doAction {
        voteRepository.getCandidates(roomId, category)
            .onSuccess { data ->
                updateState { copy(candidates = data.map { it.toUiModel() }) }
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                Timber.e(exception)
            }
    }

    private fun castVote(candidateId: Long) = doAction {
        voteRepository.castVote(candidateId)
            .onSuccess {
                sendEvent(VoteEvent.NavigateBack)
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.DATA_SAVE_FAILED)

                Timber.e(exception)
            }
    }

    @AssistedFactory
    interface Factory {
        fun create(route: Vote): VoteViewModel
    }
}