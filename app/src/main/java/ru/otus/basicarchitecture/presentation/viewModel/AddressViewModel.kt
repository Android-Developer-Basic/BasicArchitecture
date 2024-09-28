package ru.otus.basicarchitecture.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.api.AddressSuggestionsRequest
import ru.otus.basicarchitecture.api.ApiService
import ru.otus.basicarchitecture.data.WizardCache
import javax.inject.Inject

class AddressViewModel @Inject constructor(
    private val wizardCache: WizardCache,
    private val apiService: ApiService
) : ViewModel() {

    private val _addressSuggestions = MutableLiveData<List<String>>()
    val addressSuggestions: LiveData<List<String>> get() = _addressSuggestions

    private var loadJob: Job? = null

    fun save(address: String) {
        wizardCache.address = address
    }

    fun load(query: String) {
        loadJob?.cancel()
        if (query.isBlank()) {
            _addressSuggestions.value = emptyList()
            return
        }
        viewModelScope.launch {
            try {
                val response = apiService.getAddressSuggestions(AddressSuggestionsRequest(query))
                if (response.isSuccessful) {
                    response.body()?.also {
                        _addressSuggestions.value =
                            it.suggestions?.map { suggestion -> suggestion?.value.toString() }
                        Log.d("Success response", "onResponse: ${it.suggestions}")
                    }
                } else {
                    Log.d("Error response", response.toString())
                }
            } catch (e: Exception) {
                _addressSuggestions.value = emptyList()
                Log.e("!!!", "Exception: $e")
            }
        }
    }
}