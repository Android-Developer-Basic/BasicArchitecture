package ru.otus.basicarchitecture.data

import kotlinx.serialization.Serializable

@Serializable
data class AddressSuggestionsResponse(
    val suggestions: List<Address>
)
