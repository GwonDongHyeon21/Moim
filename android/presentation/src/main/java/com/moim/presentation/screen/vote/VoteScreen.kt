package com.moim.presentation.screen.vote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.R
import com.moim.presentation.navigation.Vote
import com.moim.presentation.screen.component.MoimProgressIndicator
import com.moim.presentation.screen.component.MoimTopBar
import com.moim.presentation.screen.vote.VoteScreen.REDUCTION_RATE
import com.moim.presentation.screen.vote.component.CandidateCard
import com.moim.presentation.screen.vote.component.EmptyCard
import com.moim.presentation.screen.vote.model.CandidateUiModel
import com.moim.presentation.screen.vote.model.VoteAction
import com.moim.presentation.screen.vote.model.VoteEvent
import com.moim.presentation.screen.vote.model.VoteUiState
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.theme.MoimTheme
import com.moim.presentation.util.DummyData
import com.moim.presentation.util.collectWithLifecycle
import kotlin.math.absoluteValue

private object VoteScreen {
    const val REDUCTION_RATE = 0.2f
}

@Composable
fun VoteScreen(
    route: Vote,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VoteViewModel = hiltViewModel<VoteViewModel, VoteViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(route)
        }
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {
            VoteEvent.NavigateBack -> onNavigateBack()
        }
    }

    if (uiState.isLoading) {
        MoimProgressIndicator()
    } else {
        VoteScreen(
            uiState = uiState,
            onAction = viewModel::onAction,
            modifier = modifier
        )
    }
}

@Composable
fun VoteScreen(
    uiState: VoteUiState,
    onAction: (VoteAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { uiState.candidates.size })

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            MoimTopBar(
                value = stringResource(R.string.vote),
                navigationIcon = R.drawable.arrow_back_24,
                onClickNavigationIcon = { onAction(VoteAction.NavigateBack) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = MoimPadding.PaddingMedium),
            verticalArrangement = Arrangement.spacedBy(MoimSpace.SpaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.candidates.isEmpty()) {
                EmptyCard()
            } else {
                PagerInfoSection(
                    pagerState = pagerState,
                    candidates = uiState.candidates,
                    onClickReset = { onAction(VoteAction.ResetVote) }
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                ) { page ->
                    val candidate = uiState.candidates[page]

                    CandidateCard(
                        candidate = candidate,
                        onClick = { onAction(VoteAction.CastVote(candidate.id)) },
                        modifier = Modifier.graphicsLayer {
                            val pageOffset =
                                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                            val scaleFactor =
                                1f - (pageOffset.absoluteValue * REDUCTION_RATE).coerceIn(0f, 1f)

                            scaleX = scaleFactor
                            scaleY = scaleFactor
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PagerInfoSection(
    pagerState: PagerState,
    candidates: List<CandidateUiModel>,
    onClickReset: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MoimPadding.AppHorizontalPadding)
    ) {
        Text(
            text = "${pagerState.currentPage + 1} / ${candidates.size}",
            modifier = Modifier.align(Alignment.Center)
        )
        Text(
            text = stringResource(R.string.vote_cancel),
            modifier = Modifier
                .clickable { onClickReset() }
                .align(Alignment.CenterEnd),
            color = MoimTheme.colors.gray,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VoteScreenPreview() {
    VoteScreen(
        uiState = VoteUiState(candidates = DummyData.dummyCandidates),
        onAction = {}
    )
}