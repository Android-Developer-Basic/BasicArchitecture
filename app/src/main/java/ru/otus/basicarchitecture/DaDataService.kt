package ru.otus.basicarchitecture

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface DaDataService {
    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun getSuggestions(@Body request: DaDataRequest): Response<SuggestionsResponse>

}

class FakeDaDataService : DaDataService {
    var shouldReturnError = false

    override suspend fun getSuggestions(request: DaDataRequest): Response<SuggestionsResponse> {
        return if (shouldReturnError) {
            Response.error(400, "Bad Request".toResponseBody())
        } else {
            val fakeSuggestions = listOf(Suggestion("1","2",Data("1","2","3","4","5")))
            val fakeResponse = SuggestionsResponse(fakeSuggestions)
            Response.success(fakeResponse)
        }
    }

    fun String.toResponseBody(): ResponseBody =
        ResponseBody.create(MediaType.parse("text/plain"), this)
}
