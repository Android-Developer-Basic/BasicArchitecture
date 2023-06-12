package ru.otus.basicarchitecture.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val value: String,
    @SerialName("unrestricted_value")
    val unrestrictedValue: String,
)
