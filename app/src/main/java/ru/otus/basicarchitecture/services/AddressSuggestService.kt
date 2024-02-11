package ru.otus.basicarchitecture.services

interface AddressSuggestService {
    suspend fun suggest(query: String): List<Suggestion>?
}