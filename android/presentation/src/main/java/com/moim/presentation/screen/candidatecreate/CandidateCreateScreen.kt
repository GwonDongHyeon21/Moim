package com.moim.presentation.screen.candidatecreate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.R
import com.moim.presentation.model.Category
import com.moim.presentation.navigation.CandidateCreate
import com.moim.presentation.screen.candidatecreate.CandidateCreateScreen.CONTENT_LENGTH_LIMIT
import com.moim.presentation.screen.candidatecreate.CandidateCreateScreen.MAX_LINES
import com.moim.presentation.screen.candidatecreate.model.CandidateCreateAction
import com.moim.presentation.screen.candidatecreate.model.CandidateCreateEvent
import com.moim.presentation.screen.candidatecreate.model.CandidateCreateUiState
import com.moim.presentation.screen.component.MoimButton
import com.moim.presentation.screen.component.MoimProgressIndicator
import com.moim.presentation.screen.component.MoimTopBar
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.util.collectWithLifecycle

private object CandidateCreateScreen {
    const val CONTENT_LENGTH_LIMIT = 100
    const val MAX_LINES = 1
}

@Composable
fun CandidateCreateScreen(
    route: CandidateCreate,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CandidateCreateViewModel = hiltViewModel<CandidateCreateViewModel, CandidateCreateViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(route)
        }
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.uiEvent.collectWithLifecycle { event ->
        when (event) {
            CandidateCreateEvent.NavigateBack -> onNavigateBack()
        }
    }

    CandidateCreateScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier
    )

    if (uiState.isLoading) {
        MoimProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidateCreateScreen(
    uiState: CandidateCreateUiState,
    onAction: (CandidateCreateAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val createEnabled = uiState.selectedCategory.isNotEmpty() && uiState.content.isNotEmpty()

    Scaffold(
        modifier = modifier,
        topBar = {
            MoimTopBar(
                value = stringResource(R.string.candidate_create),
                navigationIcon = R.drawable.arrow_back_24,
                onClickNavigationIcon = { onAction(CandidateCreateAction.NavigateBack) },
            )
        },
        bottomBar = {
            MoimButton(
                value = stringResource(R.string.create),
                onClick = { onAction(CandidateCreateAction.CreateCandidate) },
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                enabled = createEnabled
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = MoimPadding.AppHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(MoimSpace.SpaceSmall)
        ) {
            CategorySelectSection(
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { onAction(CandidateCreateAction.OnCategorySelected(it)) }
            )

            ContentInputSection(
                content = uiState.content,
                onContentChanged = { onAction(CandidateCreateAction.OnContentChanged(it)) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectSection(
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
            Text(text = stringResource(R.string.category))
            TextField(
                value = selectedCategory,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                readOnly = true,
                placeholder = { Text(text = stringResource(R.string.category_placeholder)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showCategory) }
            )
        }

        ExposedDropdownMenu(
            expanded = showCategory,
            onDismissRequest = { showCategory = false }
        ) {
            Category.entries.forEach { category ->
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
fun ContentInputSection(
    content: String,
    onContentChanged: (String) -> Unit
) {
    Column {
        Text(text = stringResource(R.string.candidate))
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
fun CandidateCreateScreenPreview() {
    CandidateCreateScreen(
        uiState = CandidateCreateUiState(),
        onAction = {}
    )
}