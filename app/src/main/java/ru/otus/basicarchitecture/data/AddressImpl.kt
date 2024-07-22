package ru.otus.basicarchitecture.data

import retrofit2.Response
import ru.otus.basicarchitecture.Core.Model.DTO.SuggestionRequest
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressImpl @Inject constructor(private val networkService: NetworkService) {

    suspend fun getSuggestions(input: String) = networkCall {
        networkService.getAddress(SuggestionRequest(input))
    }

    suspend inline fun <T> networkCall(crossinline block: suspend () -> Response<T>): Result<T> =
        runCatching {
            block().body() ?: throw IOException("Network error")
        }

}