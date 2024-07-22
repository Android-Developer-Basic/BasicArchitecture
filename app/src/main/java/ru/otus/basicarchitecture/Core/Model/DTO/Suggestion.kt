package ru.otus.basicarchitecture.Core.Model.DTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuggestionRequest(
    @SerialName("query")
    val query: String
)

@Serializable
data class Suggestions(
    @SerialName("suggestions")
    val suggestions: List<Suggestion>?
)

@Serializable
data class Suggestion(
    @SerialName("value")
    val value: String?
)