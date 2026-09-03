package ru.otus.basicarchitecture.address

import javax.inject.Inject
import retrofit2.HttpException
import ru.otus.basicarchitecture.model.AddressSuggestion
import ru.otus.basicarchitecture.network.dadata.AddressSuggestionsRequest
import ru.otus.basicarchitecture.network.dadata.DaDataApi
import ru.otus.basicarchitecture.network.dadata.DaDataApiKey

class DaDataAddressSuggestionsRepository @Inject constructor(
    private val api: DaDataApi,
    @DaDataApiKey private val apiKey: String,
) : AddressSuggestionsRepository {
    override suspend fun getSuggestions(query: String): List<AddressSuggestion> {
        if (apiKey.isBlank() || apiKey == "DEFAULT_API_KEY") {
            throw MissingDaDataApiKeyException()
        }

        val response = try {
            api.suggestAddress(AddressSuggestionsRequest(query = query))
        } catch (exception: HttpException) {
            if (exception.code() == 401 || exception.code() == 403) {
                throw DaDataAuthorizationException()
            }
            throw exception
        }

        return response.suggestions.map { suggestion ->
            AddressSuggestion(
                value = suggestion.value,
                unrestrictedValue = suggestion.unrestrictedValue,
                country = suggestion.data.country,
                city = suggestion.data.cityWithType ?: suggestion.data.settlementWithType,
                street = suggestion.data.streetWithType,
                house = listOfNotNull(suggestion.data.houseType, suggestion.data.house)
                    .joinToString(" ")
                    .ifBlank { null },
            )
        }
    }
}