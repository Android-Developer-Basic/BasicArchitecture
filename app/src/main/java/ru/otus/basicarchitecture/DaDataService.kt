package ru.otus.basicarchitecture

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DaDataService {
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json",
        "Authorization: Token 33c9c6d8a38493b4c05554aa083dca49db0068fd"
    )
    @POST("suggestions/api/4_1/rs/suggest/address")
    fun getSuggestions(@Body request: DaDataRequest): Call<SuggestionsResponse>

}
