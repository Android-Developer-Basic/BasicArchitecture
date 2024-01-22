package com.example.domain


import com.example.net.DaDataService
import com.example.net.data.AddressDto
// я не понимаю как избавить от зависимотей сверху, я предполагаю их быть недолжно
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DadataRepository @Inject constructor(private val dadataApi: DaDataService) {
    fun getAddressSuggestions(query: String): Flow<ViewState<AddressDto>> = flow {
        try {
            emit(ViewState.Loading)
            val response = dadataApi.getAddress(query) // я не понял как прокинуть интефейс с апишкой сюда не создавая компанион обжект
            if (response.isSuccessful) {
                emit(ViewState.Success(response.body()!!))
            } else {
                emit(ViewState.Error(RuntimeException("Failed to get address suggestions")))
            }
        } catch (e: Exception) {
            emit(ViewState.Error(e))
        }
    }
}