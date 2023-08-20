package ru.otus.basicarchitecture

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface DaDataService {
    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun getSuggestions(@Body request: DaDataRequest): Response<SuggestionsResponse>

}
