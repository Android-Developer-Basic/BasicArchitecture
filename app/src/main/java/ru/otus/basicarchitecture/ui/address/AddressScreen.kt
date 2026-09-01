package ru.otus.basicarchitecture.ui.address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.otus.basicarchitecture.R

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
        onCountryChanged = viewModel::onCountryChanged,
        onCityChanged = viewModel::onCityChanged,
        onStreetAddressChanged = viewModel::onStreetAddressChanged,
        onNextClicked = viewModel::onNextClicked,
    )
}

@Composable
private fun AddressScreen(
    uiState: AddressUiState,
    onCountryChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    onStreetAddressChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .padding(24.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                value = uiState.country,
                onValueChange = onCountryChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.country)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            )
            OutlinedTextField(
                value = uiState.city,
                onValueChange = onCityChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.city)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            )
            OutlinedTextField(
                value = uiState.streetAddress,
                onValueChange = onStreetAddressChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.street_address)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            )
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