package ru.otus.basicarchitecture.service

import retrofit2.Response
import ru.otus.basicarchitecture.service.dto.DaDataSuggestionsRequest
import ru.otus.basicarchitecture.service.dto.DaDataSuggestionsResponse

interface DaDataService {

    suspend fun getSuggestions(body: DaDataSuggestionsRequest): Response<DaDataSuggestionsResponse>

}