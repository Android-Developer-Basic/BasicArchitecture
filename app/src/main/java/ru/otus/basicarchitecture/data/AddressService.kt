package ru.otus.basicarchitecture.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddressService {
    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun getAddressSuggestions(@Body query: AddressQuery): Response<AddressSuggestionsResponse>
}