package ru.otus.basicarchitecture.model

import java.time.LocalDate

data class PersonalInfo(
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
)

data class Address(
    val country: String,
    val city: String,
    val streetAddress: String,
)

data class WizardData(
    val personalInfo: PersonalInfo? = null,
    val address: Address? = null,
    val interests: Set<String> = emptySet(),
)