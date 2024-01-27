package ru.otus.basicarchitecture.address

import dagger.hilt.android.scopes.ViewModelScoped
import ru.otus.basicarchitecture.networkCall
import ru.otus.basicarchitecture.service.DaDataService
import ru.otus.basicarchitecture.service.dto.DaDataSuggestionsRequest
import javax.inject.Inject

@ViewModelScoped
class AddressUseCase @Inject constructor() {

    @Inject
    lateinit var daDataService: DaDataService

    suspend fun getSuggestions(input: String) = networkCall {
        daDataService.getSuggestions(DaDataSuggestionsRequest(query = input))
    }

}