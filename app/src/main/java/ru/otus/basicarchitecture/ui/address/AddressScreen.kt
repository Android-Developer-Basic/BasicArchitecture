package ru.otus.basicarchitecture.ui.address

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.model.AddressSuggestion

@Composable
fun AddressRoute(
    viewModel: AddressViewModel,
    onNext: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.navigateNext.collect { onNext() }
    }

    AddressScreen(
        uiState = uiState,
        onQueryChanged = viewModel::onQueryChanged,
        onSuggestionSelected = viewModel::onSuggestionSelected,
        onRetryClicked = viewModel::onRetryClicked,
        onNextClicked = viewModel::onNextClicked,
    )
}

@Composable
private fun AddressScreen(
    uiState: AddressUiState,
    onQueryChanged: (String) -> Unit,
    onSuggestionSelected: (AddressSuggestion) -> Unit,
    onRetryClicked: () -> Unit,
    onNextClicked: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .padding(24.dp),
    ) {
        OutlinedTextField(
            value = uiState.query,
            onValueChange = onQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.address_input_label)) },
            placeholder = { Text(stringResource(R.string.address_input_placeholder)) },
            supportingText = if (
                uiState.query.isNotEmpty() &&
                uiState.query.trim().length < MIN_ADDRESS_QUERY_LENGTH
            ) {
                { Text(stringResource(R.string.address_min_characters)) }
            } else {
                null
            },
            trailingIcon = if (uiState.isLoading) {
                {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                    )
                }
            } else {
                null
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            when {
                uiState.error != null -> {
                    AddressError(
                        error = uiState.error,
                        onRetryClicked = onRetryClicked,
                    )
                }

                uiState.selectedAddress == null &&
                    uiState.hasSearched &&
                    uiState.suggestions.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.address_nothing_found),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        items(
                            items = uiState.suggestions,
                            key = AddressSuggestion::unrestrictedValue,
                        ) { suggestion ->
                            ListItem(
                                headlineContent = { Text(suggestion.value) },
                                modifier = Modifier.clickable {
                                    onSuggestionSelected(suggestion)
                                    keyboardController?.hide()
                                },
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNextClicked,
            enabled = uiState.isNextEnabled,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.next))
        }
    }
}

@Composable
private fun AddressError(
    error: AddressSearchError,
    onRetryClicked: () -> Unit,
) {
    val message = when (error) {
        AddressSearchError.ApiKeyMissing -> stringResource(R.string.dadata_api_key_missing)
        AddressSearchError.Authorization -> stringResource(R.string.dadata_authorization_error)
        AddressSearchError.Network -> stringResource(R.string.address_network_error)
        AddressSearchError.Unknown -> stringResource(R.string.address_unknown_error)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
        )
        if (error != AddressSearchError.ApiKeyMissing) {
            TextButton(onClick = onRetryClicked) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}