package ru.otus.basicarchitecture.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DadataApi {
    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun getAddressSuggestions(
        @Body request: DadataRequest
    ): Response<DadataResponse>
}