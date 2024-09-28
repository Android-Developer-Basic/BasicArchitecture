package ru.otus.basicarchitecture.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun getAddressSuggestions(
        @Body request: AddressSuggestionsRequest
    ): Response<AddressSuggestionsResponse>
}