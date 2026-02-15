package ru.otus.basicarchitecture.data.network

import retrofit2.http.Body
import retrofit2.http.POST

interface AddressApi {
    @POST("api/4_1/rs/suggest/address")
    suspend fun getHints(
        @Body query: AddressQuery
    ): AddressResponse
}