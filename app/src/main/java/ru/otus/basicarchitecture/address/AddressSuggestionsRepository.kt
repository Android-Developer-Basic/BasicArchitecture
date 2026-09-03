package ru.otus.basicarchitecture.address

import ru.otus.basicarchitecture.model.AddressSuggestion

interface AddressSuggestionsRepository {
    suspend fun getSuggestions(query: String): List<AddressSuggestion>
}

class MissingDaDataApiKeyException : IllegalStateException()

class DaDataAuthorizationException : IllegalStateException()