package ru.otus.basicarchitecture.data

import ru.otus.basicarchitecture.data.network.DadataApi
import ru.otus.basicarchitecture.data.network.DadataRequest
import javax.inject.Inject

class AddressRepository @Inject constructor(
    private val api: DadataApi
) {
    suspend fun getSuggestions(query: String): List<String> {
        return try {
            val response = api.getAddressSuggestions(DadataRequest(query))
            if (response.isSuccessful) {
                response.body()?.suggestions?.map { it.value } ?: emptyList()
            } else {
                emptyList()
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
}