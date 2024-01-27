package ru.otus.basicarchitecture.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class DaDataSuggestionsResponse(
    val suggestions: List<Suggestion>?
)
