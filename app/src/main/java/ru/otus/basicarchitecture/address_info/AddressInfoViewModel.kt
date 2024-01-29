package ru.otus.basicarchitecture.address_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.otus.basicarchitecture.data.AddressInformationData
import ru.otus.basicarchitecture.data.WizardCache
import javax.inject.Inject

@HiltViewModel
class AddressInfoViewModel @Inject constructor(
    private val cache: WizardCache
) : ViewModel() {

    val viewState: StateFlow<AddressInfoViewState> = cache.addressInfo
        .map { addAddressInfo(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, addAddressInfo(cache.addressInfo.value))

    private fun addAddressInfo(
        data: AddressInformationData
    ) = AddressInfoViewState(
        data.country,
        data.city,
        data.address,
        isValid(data.country),
        isValid(data.city),
        isValid(data.address))

    private fun isValid(text: String) = text.isNotEmpty()

    fun setCountry(country: String) {
        cache.setCountry(country)
    }

    fun setCity(city: String) {
        cache.setCity(city)
    }

    fun setAddress(address: String) {
        cache.setAddress(address)
    }
}

data class AddressInfoViewState(
    val country: String,
    val city: String,
    val address: String,
    val isValidCountry: Boolean,
    val isValidCity: Boolean,
    val isValidAddress: Boolean
)