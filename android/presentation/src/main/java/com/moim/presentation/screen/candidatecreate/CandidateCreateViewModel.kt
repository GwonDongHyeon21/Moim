package com.moim.presentation.screen.candidatecreate

import com.moim.domain.feature.vote.repository.VoteRepository
import com.moim.presentation.base.BaseViewModel
import com.moim.presentation.navigation.CandidateCreate
import com.moim.presentation.screen.candidatecreate.model.CandidateCreateAction
import com.moim.presentation.screen.candidatecreate.model.CandidateCreateEvent
import com.moim.presentation.screen.candidatecreate.model.CandidateCreateUiState
import com.moim.presentation.screen.candidatecreate.model.toUiModel
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber

@HiltViewModel(assistedFactory = CandidateCreateViewModel.Factory::class)
class CandidateCreateViewModel @AssistedInject constructor(
    @Assisted route: CandidateCreate,
    private val voteRepository: VoteRepository,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<CandidateCreateUiState, CandidateCreateEvent>(CandidateCreateUiState()) {

    override fun checkLoading() = uiState.value.isLoading
    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    private val roomId = route.roomId

    init {
        getCategories()
    }

    fun onAction(action: CandidateCreateAction) {
        when (action) {
            is CandidateCreateAction.OnCategorySelected -> updateState { copy(selectedCategory = action.category) }

            is CandidateCreateAction.OnContentChanged -> updateState { copy(content = action.content) }

            CandidateCreateAction.CreateCandidate -> createCandidate()

            CandidateCreateAction.NavigateBack -> sendEvent(CandidateCreateEvent.NavigateBack)
        }
    }

    private fun getCategories() = doAction {
        voteRepository.getCategories()
            .onSuccess { data ->
                updateState { copy(categories = data.map { it.toUiModel() }) }
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                Timber.e(exception)
            }
    }

    private fun createCandidate() = doAction {
        val category = uiState.value.selectedCategory
        val content = uiState.value.content

        if (category.isEmpty() || content.isEmpty()) return@doAction

        voteRepository.createCandidate(
            roomId = roomId,
            category = category,
            content = content
        ).onSuccess {
            sendEvent(CandidateCreateEvent.NavigateBack)
        }.onFailure { exception ->
            snackBarManager.show(SnackBarEvent.CANDIDATE_COUNT_LIMIT)

            Timber.e(exception)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(route: CandidateCreate): CandidateCreateViewModel
    }
}