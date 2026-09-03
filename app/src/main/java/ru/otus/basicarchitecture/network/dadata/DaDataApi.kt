package ru.otus.basicarchitecture.network.dadata

import retrofit2.http.Body
import retrofit2.http.POST

interface DaDataApi {
    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun suggestAddress(
        @Body request: AddressSuggestionsRequest,
    ): AddressSuggestionsResponse
}