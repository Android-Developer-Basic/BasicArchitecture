package ru.otus.basicarchitecture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.data.network.AddressQuery
import ru.otus.basicarchitecture.data.network.AddressResponse
import ru.otus.basicarchitecture.data.network.AddressSuggestion
import ru.otus.basicarchitecture.domain.AddressService
import ru.otus.basicarchitecture.domain.WizardCacheService
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val networkService: AddressService,
    private val wizardCacheService: WizardCacheService
) : ViewModel() {

    private val _hints = MutableStateFlow<List<AddressSuggestion>>(emptyList())
    val hints: StateFlow<List<AddressSuggestion>> = _hints

    fun getHints(input: String) {
        viewModelScope.launch {
            try {
                val query = AddressQuery(input)
                val response: AddressResponse = networkService.getHints(query)

                _hints.value = response.suggestions
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUserAddress(address: String) {
        wizardCacheService.updateUserAddress(
            address = address
        )
    }
}