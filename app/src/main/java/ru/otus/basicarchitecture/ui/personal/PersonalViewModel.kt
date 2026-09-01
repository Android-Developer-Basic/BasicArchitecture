package ru.otus.basicarchitecture.ui.personal

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.otus.basicarchitecture.cache.WizardCache
import ru.otus.basicarchitecture.model.PersonalInfo
import ru.otus.basicarchitecture.validation.PersonalInfoValidator
import ru.otus.basicarchitecture.validation.ValidationError

data class PersonalUiState(
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: String = "",
    val firstNameError: ValidationError? = null,
    val lastNameError: ValidationError? = null,
    val birthDateError: ValidationError? = null,
    val isNextEnabled: Boolean = false,
    val isFirstNameTouched: Boolean = false,
    val isLastNameTouched: Boolean = false,
    val isBirthDateTouched: Boolean = false,
)

sealed interface PersonalUiEffect {
    data class ShowValidationError(val error: ValidationError) : PersonalUiEffect

    data object NavigateNext : PersonalUiEffect
}

class PersonalViewModel @Inject constructor(
    private val wizardCache: WizardCache,
    private val validator: PersonalInfoValidator,
) : ViewModel() {
    private val cachedPersonalInfo = wizardCache.data.value.personalInfo
    private val mutableUiState = MutableStateFlow(
        PersonalUiState(
            firstName = cachedPersonalInfo?.firstName.orEmpty(),
            lastName = cachedPersonalInfo?.lastName.orEmpty(),
            birthDate = cachedPersonalInfo?.birthDate
                ?.format(PersonalInfoValidator.DATE_FORMATTER)
                ?.filter(Char::isDigit)
                .orEmpty(),
        ).withValidation(),
    )
    val uiState: StateFlow<PersonalUiState> = mutableUiState.asStateFlow()

    private val mutableEffects = MutableSharedFlow<PersonalUiEffect>(extraBufferCapacity = 1)
    val effects: SharedFlow<PersonalUiEffect> = mutableEffects.asSharedFlow()

    fun onFirstNameChanged(value: String) {
        updateState { copy(firstName = value) }
    }

    fun onLastNameChanged(value: String) {
        updateState { copy(lastName = value) }
    }

    fun onBirthDateChanged(value: String) {
        updateState { copy(birthDate = value.filter(Char::isDigit).take(MAX_DATE_DIGITS)) }
    }

    fun onFirstNameFocusLost() {
        updateState { copy(isFirstNameTouched = true) }
        mutableUiState.value.firstNameError?.let(::showValidationError)
    }

    fun onLastNameFocusLost() {
        updateState { copy(isLastNameTouched = true) }
        mutableUiState.value.lastNameError?.let(::showValidationError)
    }

    fun onBirthDateFocusLost() {
        updateState { copy(isBirthDateTouched = true) }
        mutableUiState.value.birthDateError?.let(::showValidationError)
    }

    fun onNextClicked() {
        val state = mutableUiState.value
        val validation = validator.validate(
            state.firstName,
            state.lastName,
            formatDateForValidation(state.birthDate),
        )
        val birthDate = validation.birthDate
        if (!validation.isValid || birthDate == null) return

        wizardCache.savePersonalInfo(
            PersonalInfo(
                firstName = state.firstName.trim(),
                lastName = state.lastName.trim(),
                birthDate = birthDate,
            ),
        )
        mutableEffects.tryEmit(PersonalUiEffect.NavigateNext)
    }

    private fun updateState(transform: PersonalUiState.() -> PersonalUiState) {
        mutableUiState.update { current -> current.transform().withValidation() }
    }

    private fun PersonalUiState.withValidation(): PersonalUiState {
        val result = validator.validate(
            firstName,
            lastName,
            formatDateForValidation(birthDate),
        )
        return copy(
            firstNameError = result.firstNameError.takeIf { isFirstNameTouched },
            lastNameError = result.lastNameError.takeIf { isLastNameTouched },
            birthDateError = result.birthDateError.takeIf { isBirthDateTouched },
            isNextEnabled = result.isValid,
        )
    }

    private fun showValidationError(error: ValidationError) {
        mutableEffects.tryEmit(PersonalUiEffect.ShowValidationError(error))
    }

    private fun formatDateForValidation(digits: String): String {
        return buildString {
            digits.forEachIndexed { index, character ->
                append(character)
                if ((index == 1 || index == 3) && index != digits.lastIndex) append('.')
            }
        }
    }

    private companion object {
        const val MAX_DATE_DIGITS = 8
    }
}