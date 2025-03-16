package ru.otus.basicarchitecture.model

data class AddressSuggestionRequest(val query: String)
data class DadataResponse(val suggestions: List<AddressSuggestion>)
data class AddressSuggestion(val value: String)
