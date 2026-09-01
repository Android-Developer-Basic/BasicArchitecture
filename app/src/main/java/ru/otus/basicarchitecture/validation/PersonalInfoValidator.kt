package ru.otus.basicarchitecture.validation

import java.time.Clock
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle
import javax.inject.Inject

enum class ValidationError {
    Required,
    InvalidDate,
    FutureDate,
    UnderEighteen,
}

data class PersonalValidationResult(
    val firstNameError: ValidationError? = null,
    val lastNameError: ValidationError? = null,
    val birthDateError: ValidationError? = null,
    val birthDate: LocalDate? = null,
) {
    val isValid: Boolean
        get() = firstNameError == null &&
            lastNameError == null &&
            birthDateError == null &&
            birthDate != null
}

class PersonalInfoValidator @Inject constructor(
    private val clock: Clock,
) {
    fun validate(
        firstName: String,
        lastName: String,
        birthDateText: String,
    ): PersonalValidationResult {
        val firstNameError = ValidationError.Required.takeIf { firstName.isBlank() }
        val lastNameError = ValidationError.Required.takeIf { lastName.isBlank() }
        val parsedBirthDate = parseDate(birthDateText)
        val today = LocalDate.now(clock)
        val birthDateError = when {
            birthDateText.isBlank() -> ValidationError.Required
            parsedBirthDate == null -> ValidationError.InvalidDate
            parsedBirthDate.isAfter(today) -> ValidationError.FutureDate
            parsedBirthDate.plusYears(18).isAfter(today) -> ValidationError.UnderEighteen
            else -> null
        }

        return PersonalValidationResult(
            firstNameError = firstNameError,
            lastNameError = lastNameError,
            birthDateError = birthDateError,
            birthDate = parsedBirthDate,
        )
    }

    private fun parseDate(value: String): LocalDate? = try {
        LocalDate.parse(value, DATE_FORMATTER)
    } catch (_: DateTimeParseException) {
        null
    }

    companion object {
        val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter
            .ofPattern("dd.MM.uuuu")
            .withResolverStyle(ResolverStyle.STRICT)
    }
}