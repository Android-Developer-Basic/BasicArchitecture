package ru.otus.basicarchitecture.network

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DadataApi {
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun getAddressSuggestions(@Body request: AddressSuggestionRequest): DadataResponse
}

data class AddressSuggestionRequest(val query: String)

data class DadataResponse(val suggestions: List<AddressSuggestion>)
data class AddressSuggestion(val value: String)
