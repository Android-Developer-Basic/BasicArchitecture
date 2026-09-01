package ru.otus.basicarchitecture.ui.address

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.otus.basicarchitecture.cache.WizardCache
import ru.otus.basicarchitecture.model.Address

data class AddressUiState(
    val country: String = "",
    val city: String = "",
    val streetAddress: String = "",
) {
    val isNextEnabled: Boolean
        get() = country.isNotBlank() && city.isNotBlank() && streetAddress.isNotBlank()
}

class AddressViewModel @Inject constructor(
    private val wizardCache: WizardCache,
) : ViewModel() {
    private val cachedAddress = wizardCache.data.value.address
    private val mutableUiState = MutableStateFlow(
        AddressUiState(
            country = cachedAddress?.country.orEmpty(),
            city = cachedAddress?.city.orEmpty(),
            streetAddress = cachedAddress?.streetAddress.orEmpty(),
        ),
    )
    val uiState: StateFlow<AddressUiState> = mutableUiState.asStateFlow()

    private val mutableNavigateNext = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateNext: SharedFlow<Unit> = mutableNavigateNext.asSharedFlow()

    fun onCountryChanged(value: String) {
        mutableUiState.update { it.copy(country = value) }
    }

    fun onCityChanged(value: String) {
        mutableUiState.update { it.copy(city = value) }
    }

    fun onStreetAddressChanged(value: String) {
        mutableUiState.update { it.copy(streetAddress = value) }
    }

    fun onNextClicked() {
        val state = mutableUiState.value
        if (!state.isNextEnabled) return

        wizardCache.saveAddress(
            Address(
                country = state.country.trim(),
                city = state.city.trim(),
                streetAddress = state.streetAddress.trim(),
            ),
        )
        mutableNavigateNext.tryEmit(Unit)
    }
}