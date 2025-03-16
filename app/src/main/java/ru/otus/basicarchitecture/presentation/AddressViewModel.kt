package ru.otus.basicarchitecture.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.api.AddressApiService.api
import ru.otus.basicarchitecture.data.WizardCache
import ru.otus.basicarchitecture.model.AddressSuggestionRequest
import javax.inject.Inject

class AddressViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {
    val address = MutableLiveData<String>()
    val addressSuggestions = MutableLiveData<List<String>>()
    fun loadSuggestions(search: String) {
        if (search.isBlank()) {
            addressSuggestions.value = emptyList()
            return
        }

        viewModelScope.launch {
            try {
                val response = api.getAddressSuggestions(AddressSuggestionRequest(search))
                addressSuggestions.value = response.suggestions.map { it.value }
            } catch (e: Exception) {
                addressSuggestions.value = emptyList()
            }
        }
    }


    fun saveToCash() {
        wizardCache.address = address.value ?: ""
    }
}