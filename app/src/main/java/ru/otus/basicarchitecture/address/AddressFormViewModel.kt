package ru.otus.basicarchitecture.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.data.WizardCache
import ru.otus.basicarchitecture.data.Address
import ru.otus.basicarchitecture.data.AddressQuery
import ru.otus.basicarchitecture.data.AddressService
import java.net.UnknownHostException
import javax.inject.Inject

class AddressFormViewModel(
    private val wizardCache: WizardCache,
    private val addressService: AddressService
): ViewModel() {
    private val _addresses = MutableLiveData<List<Address>>(listOf())
    val addresses: LiveData<List<Address>> = _addresses

    private var searchJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            try {
                val response = addressService.getAddressSuggestions(AddressQuery(query))
                response.body()?.also {
                    _addresses.value = it.suggestions
                } ?: {
                    _addresses.value = emptyList()
                }
            } catch (e: UnknownHostException) {
                _addresses.value = emptyList()
            }
        }
    }

    fun save(address: String) {
        wizardCache.address = address
    }

    class Factory @Inject constructor(
        private val wizardCache: WizardCache,
        private val addressService: AddressService
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddressFormViewModel::class.java)) {
                return AddressFormViewModel(wizardCache, addressService) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}