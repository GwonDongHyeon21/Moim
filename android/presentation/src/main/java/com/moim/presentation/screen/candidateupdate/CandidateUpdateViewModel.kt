package com.moim.presentation.screen.candidateupdate

import com.moim.presentation.base.BaseViewModel
import com.moim.presentation.navigation.CandidateUpdate
import com.moim.presentation.screen.candidateupdate.model.CandidateUpdateAction
import com.moim.presentation.screen.candidateupdate.model.CandidateUpdateEvent
import com.moim.presentation.screen.candidateupdate.model.CandidateUpdateUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = CandidateUpdateViewModel.Factory::class)
class CandidateUpdateViewModel @AssistedInject constructor(
    @Assisted route: CandidateUpdate
) : BaseViewModel<CandidateUpdateUiState, CandidateUpdateEvent>(CandidateUpdateUiState()) {

    override fun checkLoading() = uiState.value.isLoading
    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    init {
        loadMyCandidates(route.category)
    }

    fun onAction(action: CandidateUpdateAction) {
        when (action) {
            CandidateUpdateAction.NavigateBack -> Unit
            is CandidateUpdateAction.OnCategorySelected -> Unit
            is CandidateUpdateAction.OnContentChanged -> Unit
            CandidateUpdateAction.UpdateCandidate -> Unit
        }
    }

    private fun loadMyCandidates(category: String) = doAction {

    }


    @AssistedFactory
    interface Factory {
        fun create(route: CandidateUpdate): CandidateUpdateViewModel
    }
}