package ru.otus.basicarchitecture.ui.interests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.otus.basicarchitecture.R

@Composable
fun InterestsRoute(
    viewModel: InterestsViewModel,
    onNext: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.navigateNext.collect { onNext() }
    }

    InterestsScreen(
        uiState = uiState,
        onInterestClicked = viewModel::onInterestClicked,
        onNextClicked = viewModel::onNextClicked,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InterestsScreen(
    uiState: InterestsUiState,
    onInterestClicked: (String) -> Unit,
    onNextClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(24.dp),
    ) {
        Text(stringResource(R.string.interests_hint))
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                uiState.availableInterests.forEach { interest ->
                    FilterChip(
                        selected = interest in uiState.selectedInterests,
                        onClick = { onInterestClicked(interest) },
                        label = { Text(interest) },
                    )
                }
            }
        }
        Button(
            onClick = onNextClicked,
            enabled = uiState.isNextEnabled,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.next))
        }
    }
}