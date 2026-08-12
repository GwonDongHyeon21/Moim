package com.moim.presentation.screen.candidateupdate

import com.moim.domain.feature.vote.model.UpdateCandidateParams
import com.moim.domain.feature.vote.repository.VoteRepository
import com.moim.presentation.base.BaseViewModel
import com.moim.presentation.model.toUiModel
import com.moim.presentation.navigation.CandidateUpdate
import com.moim.presentation.screen.candidateupdate.model.CandidateUpdateAction
import com.moim.presentation.screen.candidateupdate.model.CandidateUpdateEvent
import com.moim.presentation.screen.candidateupdate.model.CandidateUpdateUiState
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

@HiltViewModel(assistedFactory = CandidateUpdateViewModel.Factory::class)
class CandidateUpdateViewModel @AssistedInject constructor(
    @Assisted route: CandidateUpdate,
    private val voteRepository: VoteRepository,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<CandidateUpdateUiState, CandidateUpdateEvent>(CandidateUpdateUiState()) {

    override fun checkLoading() = uiState.value.isLoading
    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    val roomId = route.roomId
    val category = route.category

    init {
        loadInitialData()
    }

    fun onAction(action: CandidateUpdateAction) {
        when (action) {
            CandidateUpdateAction.NavigateBack -> sendEvent(CandidateUpdateEvent.NavigateBack)

            is CandidateUpdateAction.OnCategorySelected -> updateState {
                val newCandidates = newCandidates.toMutableList().apply {
                    this[action.num] = this[action.num].copy(category = action.category)
                }
                copy(newCandidates = newCandidates)
            }

            is CandidateUpdateAction.OnContentChanged -> updateState {
                val newCandidates = newCandidates.toMutableList().apply {
                    this[action.num] = this[action.num].copy(content = action.content)
                }
                copy(newCandidates = newCandidates)
            }

            CandidateUpdateAction.UpdateCandidate -> updateCandidates()
        }
    }

    private fun loadInitialData() = doAction {
        coroutineScope {
            val categoriesDeferred = async { voteRepository.getCategories() }
            val candidatesDeferred = async { voteRepository.getMyCandidates(roomId, category) }

            val categoriesResult = categoriesDeferred.await()
            val candidatesResult = candidatesDeferred.await()

            if (categoriesResult.isSuccess && candidatesResult.isSuccess) {
                val categories = categoriesResult.getOrNull()?.map { it.toUiModel() } ?: emptyList()
                val candidates = candidatesResult.getOrNull()?.map { it.toUiModel() } ?: emptyList()

                updateState {
                    copy(
                        categories = categories,
                        originalCandidates = candidates,
                        newCandidates = candidates
                    )
                }
            } else {
                sendEvent(CandidateUpdateEvent.NavigateBack)
                snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                val exception =
                    categoriesResult.exceptionOrNull() ?: candidatesResult.exceptionOrNull()
                Timber.e(exception)
            }
        }
    }

    private fun updateCandidates() {
        val originals = uiState.value.originalCandidates
        val news = uiState.value.newCandidates

        val changedCandidates = news.filterIndexed { index, new ->
            val original = originals.getOrNull(index)
            original != new
        }

        if (changedCandidates.isEmpty()) {
            sendEvent(CandidateUpdateEvent.NavigateBack)

            return
        }

        doAction {
            voteRepository.updateCandidates(
                roomId = roomId,
                candidates = changedCandidates.map { candidate ->
                    UpdateCandidateParams(
                        id = candidate.id,
                        category = candidate.category,
                        content = candidate.content
                    )
                }
            ).onSuccess {
                sendEvent(CandidateUpdateEvent.NavigateBack)
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.NETWORK_ERROR)

                Timber.e(exception)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(route: CandidateUpdate): CandidateUpdateViewModel
    }
}