package com.moim.presentation.screen.candidateupdate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.R
import com.moim.presentation.model.CategoryUiModel
import com.moim.presentation.navigation.CandidateUpdate
import com.moim.presentation.screen.candidateupdate.CandidateUpdateScreen.CONTENT_LENGTH_LIMIT
import com.moim.presentation.screen.candidateupdate.CandidateUpdateScreen.MAX_LINES
import com.moim.presentation.screen.candidateupdate.model.CandidateUpdateAction
import com.moim.presentation.screen.candidateupdate.model.CandidateUpdateEvent
import com.moim.presentation.screen.candidateupdate.model.CandidateUpdateUiState
import com.moim.presentation.screen.component.MoimBottomBarButton
import com.moim.presentation.screen.component.MoimProgressIndicator
import com.moim.presentation.screen.component.MoimTopBar
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.util.DummyData
import com.moim.presentation.util.collectWithLifecycle

private object CandidateUpdateScreen {
    const val CONTENT_LENGTH_LIMIT = 100
    const val MAX_LINES = 1
}

@Composable
fun CandidateUpdateScreen(
    route: CandidateUpdate,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CandidateUpdateViewModel = hiltViewModel<CandidateUpdateViewModel, CandidateUpdateViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(route)
        }
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {
            CandidateUpdateEvent.NavigateBack -> onNavigateBack()
        }
    }

    if (uiState.isLoading) {
        MoimProgressIndicator()
    } else {
        CandidateUpdateScreen(
            uiState = uiState,
            onAction = viewModel::onAction,
            modifier = modifier
        )
    }
}

@Composable
fun CandidateUpdateScreen(
    uiState: CandidateUpdateUiState,
    onAction: (CandidateUpdateAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { uiState.newCandidates.size }

    Scaffold(
        modifier = modifier,
        topBar = {
            MoimTopBar(
                value = stringResource(R.string.candidate_update_title),
                navigationIcon = R.drawable.arrow_back_24,
                onClickNavigationIcon = { onAction(CandidateUpdateAction.NavigateBack) },
            )
        },
        bottomBar = {
            MoimBottomBarButton(
                value = stringResource(R.string.candidate_update),
                onClick = { onAction(CandidateUpdateAction.UpdateCandidate) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${pagerState.currentPage + 1} / ${pagerState.pageCount}")

            HorizontalPager(pagerState) { page ->
                val candidate = uiState.newCandidates[page]
                Column(
                    modifier = Modifier.padding(horizontal = MoimPadding.AppHorizontalPadding),
                    verticalArrangement = Arrangement.spacedBy(MoimSpace.SpaceSmall)
                ) {
                    CategorySelectSection(
                        categories = uiState.categories,
                        selectedCategory = candidate.category,
                        onCategorySelected = {
                            onAction(CandidateUpdateAction.OnCategorySelected(page, it))
                        }
                    )

                    ContentInputSection(
                        content = candidate.content,
                        onContentChanged = {
                            onAction(CandidateUpdateAction.OnContentChanged(page, it))
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategorySelectSection(
    categories: List<CategoryUiModel>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var showCategory by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = showCategory,
        onExpandedChange = { showCategory = it },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(text = stringResource(R.string.candidate_update_category))
            TextField(
                value = selectedCategory,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                readOnly = true,
                placeholder = { Text(text = stringResource(R.string.candidate_update_category_placeholder)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showCategory) }
            )
        }

        ExposedDropdownMenu(
            expanded = showCategory,
            onDismissRequest = { showCategory = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(text = category.name) },
                    onClick = {
                        onCategorySelected(category.name)
                        showCategory = false
                    }
                )
            }
        }
    }
}

@Composable
private fun ContentInputSection(
    content: String,
    onContentChanged: (String) -> Unit
) {
    Column {
        Text(text = stringResource(R.string.candidate_update_candidate))
        TextField(
            value = content,
            onValueChange = { if (it.length <= CONTENT_LENGTH_LIMIT) onContentChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = MAX_LINES
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CandidateUpdateScreenPreview() {
    CandidateUpdateScreen(
        uiState = CandidateUpdateUiState(newCandidates = DummyData.dummyCandidates),
        onAction = {}
    )
}