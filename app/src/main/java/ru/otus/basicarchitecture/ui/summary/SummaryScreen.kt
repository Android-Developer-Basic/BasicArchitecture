package ru.otus.basicarchitecture.ui.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.model.WizardData
import ru.otus.basicarchitecture.validation.PersonalInfoValidator

@Composable
fun SummaryRoute(
    viewModel: SummaryViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SummaryScreen(uiState = uiState)
}

@Composable
private fun SummaryScreen(
    uiState: WizardData,
) {
    val notSpecified = stringResource(R.string.not_specified)
    val personalInfo = uiState.personalInfo
    val address = uiState.address

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .systemBarsPadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            ) { 
            item {
                SummaryItem(
                    label = stringResource(R.string.first_name),
                    value = personalInfo?.firstName ?: notSpecified,
                )
            }
            item {
                SummaryItem(
                    label = stringResource(R.string.last_name),
                    value = personalInfo?.lastName ?: notSpecified,
                )
            }
            item {
                SummaryItem(
                    label = stringResource(R.string.birth_date),
                    value = personalInfo?.birthDate
                        ?.format(PersonalInfoValidator.DATE_FORMATTER)
                        ?: notSpecified,
                )
            }
            item {
                SummaryItem(
                    label = stringResource(R.string.address_title),
                    value = address?.let {
                        listOf(it.country, it.city, it.streetAddress).joinToString(", ")
                    } ?: notSpecified,
                )
            }
            item {
                SummaryItem(
                    label = stringResource(R.string.interests_title),
                    value = uiState.interests.takeIf(Set<String>::isNotEmpty)
                        ?.joinToString(", ")
                        ?: notSpecified,
                )
            }
        }
    }
}

@Composable
private fun SummaryItem(
    label: String,
    value: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        HorizontalDivider()
    }
}