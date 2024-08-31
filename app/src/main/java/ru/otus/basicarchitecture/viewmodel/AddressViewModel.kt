package ru.otus.basicarchitecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.cache.Address
import ru.otus.basicarchitecture.cache.Person
import ru.otus.basicarchitecture.cache.WizardCache
import ru.otus.basicarchitecture.dadata.AddressSuggestionRequest
import ru.otus.basicarchitecture.dadata.DadataApiService
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {

    private val _addressSuggestions = MutableLiveData<List<String>>()
    val addressSuggestions: LiveData<List<String>> get() = _addressSuggestions

    private val _state =MutableLiveData<Address>()
    val state : LiveData<Address> get() = _state


    init {
        state.observeForever { validateForm() }
    }

    private fun validateForm():Boolean{
        return state.value?.address.isNullOrBlank()

    }

    fun fetchAddressSuggestions(query: String) {
        if (query.isBlank()) {
            _addressSuggestions.value = emptyList()
            return
        }

        viewModelScope.launch {
            try {
                val response = DadataApiService.api.getAddressSuggestions(AddressSuggestionRequest(query))
                _addressSuggestions.value = response.suggestions.map { it.value }
            } catch (e: Exception) {
                _addressSuggestions.value = emptyList()
            }
        }
    }

    fun saveData() {
        wizardCache.address = state.value
    }


    fun updateCity(city : String){
        val currentState = _state.value ?: Address()
        _state.setValue(currentState.copy(city = city))
        saveData()
    }

    fun updateCountry(country : String){
        val currentState = _state.value ?: Address()
        _state.value=currentState.copy(country =country)
        saveData()
    }

    fun updateAddress(address : String){
        val currentState = _state.value ?: Address()
        _state.setValue(currentState.copy(address = address))
        saveData()
    }
}