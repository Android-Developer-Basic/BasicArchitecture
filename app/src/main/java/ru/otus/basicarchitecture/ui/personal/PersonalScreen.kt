package ru.otus.basicarchitecture.ui.personal

import android.widget.Toast
import androidx.annotation.StringRes
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.validation.ValidationError

@Composable
fun PersonalRoute(
    viewModel: PersonalViewModel,
    onNext: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                PersonalUiEffect.NavigateNext -> onNext()
                is PersonalUiEffect.ShowValidationError -> Toast.makeText(
                    context,
                    context.getString(effect.error.stringResource),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    PersonalScreen(
        uiState = uiState,
        onFirstNameChanged = viewModel::onFirstNameChanged,
        onLastNameChanged = viewModel::onLastNameChanged,
        onBirthDateChanged = viewModel::onBirthDateChanged,
        onFirstNameFocusLost = viewModel::onFirstNameFocusLost,
        onLastNameFocusLost = viewModel::onLastNameFocusLost,
        onBirthDateFocusLost = viewModel::onBirthDateFocusLost,
        onNextClicked = viewModel::onNextClicked,
    )
}

@Composable
private fun PersonalScreen(
    uiState: PersonalUiState,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onBirthDateChanged: (String) -> Unit,
    onFirstNameFocusLost: () -> Unit,
    onLastNameFocusLost: () -> Unit,
    onBirthDateFocusLost: () -> Unit,
    onNextClicked: () -> Unit,
) {
    var firstNameWasFocused by rememberSaveable { mutableStateOf(false) }
    var lastNameWasFocused by rememberSaveable { mutableStateOf(false) }
    var birthDateWasFocused by rememberSaveable { mutableStateOf(false) }

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
                value = uiState.firstName,
                onValueChange = onFirstNameChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { state ->
                        if (state.isFocused) firstNameWasFocused = true
                        else if (firstNameWasFocused) onFirstNameFocusLost()
                    },
                label = { Text(stringResource(R.string.first_name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                isError = uiState.firstNameError != null,
                supportingText = uiState.firstNameError?.let { error ->
                    { ValidationErrorText(error) }
                },
            )
            OutlinedTextField(
                value = uiState.lastName,
                onValueChange = onLastNameChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { state ->
                        if (state.isFocused) lastNameWasFocused = true
                        else if (lastNameWasFocused) onLastNameFocusLost()
                    },
                label = { Text(stringResource(R.string.last_name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                isError = uiState.lastNameError != null,
                supportingText = uiState.lastNameError?.let { error ->
                    { ValidationErrorText(error) }
                },
            )
            OutlinedTextField(
                value = uiState.birthDate,
                onValueChange = onBirthDateChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { state ->
                        if (state.isFocused) birthDateWasFocused = true
                        else if (birthDateWasFocused) onBirthDateFocusLost()
                    },
                label = { Text(stringResource(R.string.birth_date)) },
                placeholder = { Text(stringResource(R.string.birth_date_placeholder)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = DateVisualTransformation,
                isError = uiState.birthDateError != null,
                supportingText = uiState.birthDateError?.let { error ->
                    { ValidationErrorText(error) }
                },
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

@Composable
private fun ValidationErrorText(error: ValidationError) {
    Text(
        text = stringResource(error.stringResource),
        color = MaterialTheme.colorScheme.error,
    )
}

private val ValidationError.stringResource: Int
    @StringRes get() = when (this) {
        ValidationError.Required -> R.string.required_field
        ValidationError.InvalidDate -> R.string.invalid_date
        ValidationError.FutureDate -> R.string.future_date
        ValidationError.UnderEighteen -> R.string.under_eighteen
    }

private data object DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.take(MAX_DATE_DIGITS)
        val formatted = buildString {
            digits.forEachIndexed { index, character ->
                append(character)
                if ((index == 1 || index == 3) && index != digits.lastIndex) append('.')
            }
        }
        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = DateOffsetMapping(digits.length),
        )
    }
}

private class DateOffsetMapping(
    private val originalLength: Int,
) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        val safeOffset = offset.coerceIn(0, originalLength)
        return when {
            safeOffset <= 2 -> safeOffset
            safeOffset <= 4 -> safeOffset + 1
            else -> safeOffset + 2
        }
    }

    override fun transformedToOriginal(offset: Int): Int {
        val separatorCount = (if (originalLength > 2) 1 else 0) +
            (if (originalLength > 4) 1 else 0)
        val safeOffset = offset.coerceIn(0, originalLength + separatorCount)
        return when {
            safeOffset <= 2 -> safeOffset
            safeOffset <= 5 -> safeOffset - 1
            else -> safeOffset - 2
        }.coerceIn(0, originalLength)
    }
}

private const val MAX_DATE_DIGITS = 8