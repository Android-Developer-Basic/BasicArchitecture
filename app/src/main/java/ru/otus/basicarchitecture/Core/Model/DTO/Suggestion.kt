package ru.otus.basicarchitecture.Core.Model.DTO

import kotlinx.serialization.Serializable

@Serializable
data class SuggestionRequest(
    val query: String
)

@Serializable
data class Suggestions(
    val suggestions: List<Suggestion>?
)

@Serializable
data class Suggestion(
    val value: String?
)