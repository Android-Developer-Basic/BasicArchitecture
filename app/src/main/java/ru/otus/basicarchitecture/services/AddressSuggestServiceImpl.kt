package ru.otus.basicarchitecture.services

import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
interface AddressViewModelModule {
    @Binds
    @ViewModelScoped
    fun service(impl: AddressSuggestServiceImpl): AddressSuggestService
}

class AddressSuggestServiceImpl @Inject constructor(
    private val dadataApiService: DadataApiService
) : AddressSuggestService {
    override suspend fun suggest(query: String): List<Suggestion>? {
        try {
            val response = dadataApiService.suggestAddress(DaDataSuggestionsRequest(query))
            if (response.isSuccessful){
                return response.body()?.suggestions
            }
        }
        catch (e: Exception){
            Log.e(TAG, "Exception: ${e.message}", e)
        }

        return emptyList()
    }
}

private const val TAG = "AddressSuggestService"