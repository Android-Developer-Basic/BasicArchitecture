import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DaDataApi {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Token 1f17bb43e8c5a22a491f517c8cf95a70704bb456"
    )
    @POST("suggest/address")
    suspend fun suggestAddress(@Body request: SuggestRequest): SuggestResponse
}

data class SuggestRequest(val query: String)

data class SuggestResponse(val suggestions: List<Suggestion>)

data class Suggestion(val value: String)