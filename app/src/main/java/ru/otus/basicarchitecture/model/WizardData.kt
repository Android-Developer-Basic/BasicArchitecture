package ru.otus.basicarchitecture.model

import java.time.LocalDate

data class PersonalInfo(
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
)

data class Address(
    val value: String,
    val unrestrictedValue: String,
    val country: String?,
    val city: String?,
    val street: String?,
    val house: String?,
)

data class AddressSuggestion(
    val value: String,
    val unrestrictedValue: String,
    val country: String?,
    val city: String?,
    val street: String?,
    val house: String?,
) {
    fun toAddress(): Address = Address(
        value = value,
        unrestrictedValue = unrestrictedValue,
        country = country,
        city = city,
        street = street,
        house = house,
    )
}

data class WizardData(
    val personalInfo: PersonalInfo? = null,
    val address: Address? = null,
    val interests: Set<String> = emptySet(),
)