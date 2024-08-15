package ru.otus.basicarchitecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.network.AddressSuggestionRequest
import ru.otus.basicarchitecture.network.DadataApiService
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Inject

@HiltViewModel
class Fragment2ViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {

    val address = MutableLiveData<String>()
    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> get() = _isFormValid

    private val _addressSuggestions = MutableLiveData<List<String>>()
    val addressSuggestions: LiveData<List<String>> get() = _addressSuggestions

    init {
        address.observeForever { validateForm() }
    }

    private fun validateForm() {
        val isAddressValid = !address.value.isNullOrBlank()
        _isFormValid.value = isAddressValid
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
        val addressParts = address.value?.split(",")?.map { it.trim() }
        wizardCache.country = addressParts?.getOrNull(0)
        wizardCache.city = addressParts?.getOrNull(1)
        wizardCache.address = addressParts?.getOrNull(2)
    }
}
