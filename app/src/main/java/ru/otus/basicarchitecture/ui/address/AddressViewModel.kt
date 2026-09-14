package ru.otus.basicarchitecture.ui.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.address.AddressSuggestionsRepository
import ru.otus.basicarchitecture.address.DaDataAuthorizationException
import ru.otus.basicarchitecture.address.MissingDaDataApiKeyException
import ru.otus.basicarchitecture.cache.WizardCache
import ru.otus.basicarchitecture.model.Address
import ru.otus.basicarchitecture.model.AddressSuggestion

private const val SEARCH_DEBOUNCE_MILLIS = 400L
const val MIN_ADDRESS_QUERY_LENGTH = 3

enum class AddressSearchError {
    ApiKeyMissing,
    Authorization,
    Network,
    Unknown,
}

data class AddressUiState(
    val query: String = "",
    val suggestions: List<AddressSuggestion> = emptyList(),
    val selectedAddress: Address? = null,
    val isLoading: Boolean = false,
    val hasSearched: Boolean = false,
    val error: AddressSearchError? = null,
) {
    val isNextEnabled: Boolean
        get() = selectedAddress != null && query == selectedAddress.value
}

class AddressViewModel @Inject constructor(
    private val wizardCache: WizardCache,
    private val addressSuggestionsRepository: AddressSuggestionsRepository,
) : ViewModel() {
    private val cachedAddress = wizardCache.data.value.address
    private val mutableUiState = MutableStateFlow(
        AddressUiState(
            query = cachedAddress?.value.orEmpty(),
            selectedAddress = cachedAddress,
        ),
    )
    val uiState: StateFlow<AddressUiState> = mutableUiState.asStateFlow()

    private val mutableNavigateNext = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateNext: SharedFlow<Unit> = mutableNavigateNext.asSharedFlow()

    private var suggestionsJob: Job? = null

    fun onQueryChanged(value: String) {
        val selectedAddress = mutableUiState.value.selectedAddress
            ?.takeIf { address -> address.value == value }

        mutableUiState.update {
            it.copy(
                query = value,
                suggestions = emptyList(),
                selectedAddress = selectedAddress,
                isLoading = false,
                hasSearched = false,
                error = null,
            )
        }

        suggestionsJob?.cancel()
        if (value.trim().length < MIN_ADDRESS_QUERY_LENGTH || selectedAddress != null) return

        suggestionsJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MILLIS)
            loadSuggestions(value.trim())
        }
    }

    fun onSuggestionSelected(suggestion: AddressSuggestion) {
        suggestionsJob?.cancel()
        mutableUiState.update {
            it.copy(
                query = suggestion.value,
                suggestions = emptyList(),
                selectedAddress = suggestion.toAddress(),
                isLoading = false,
                hasSearched = false,
                error = null,
            )
        }
    }

    fun onRetryClicked() {
        val query = mutableUiState.value.query.trim()
        if (query.length < MIN_ADDRESS_QUERY_LENGTH) return

        suggestionsJob?.cancel()
        suggestionsJob = viewModelScope.launch { loadSuggestions(query) }
    }

    fun onNextClicked() {
        val state = mutableUiState.value
        val address = state.selectedAddress ?: return
        if (!state.isNextEnabled) return

        suggestionsJob?.cancel()
        wizardCache.saveAddress(address)
        mutableNavigateNext.tryEmit(Unit)
    }

    private suspend fun loadSuggestions(query: String) {
        mutableUiState.update {
            it.copy(
                suggestions = emptyList(),
                isLoading = true,
                hasSearched = false,
                error = null,
            )
        }

        try {
            val suggestions = addressSuggestionsRepository.getSuggestions(query)
            if (mutableUiState.value.query.trim() == query) {
                mutableUiState.update {
                    it.copy(
                        suggestions = suggestions,
                        isLoading = false,
                        hasSearched = true,
                    )
                }
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (_: MissingDaDataApiKeyException) {
            showError(query, AddressSearchError.ApiKeyMissing)
        } catch (_: DaDataAuthorizationException) {
            showError(query, AddressSearchError.Authorization)
        } catch (_: IOException) {
            showError(query, AddressSearchError.Network)
        } catch (_: Exception) {
            showError(query, AddressSearchError.Unknown)
        }
    }

    private fun showError(query: String, error: AddressSearchError) {
        if (mutableUiState.value.query.trim() != query) return
        mutableUiState.update {
            it.copy(
                suggestions = emptyList(),
                isLoading = false,
                hasSearched = true,
                error = error,
            )
        }
    }
}