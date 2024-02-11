package ru.otus.basicarchitecture.address_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.data.AddressInformationData
import ru.otus.basicarchitecture.data.WizardCache
import ru.otus.basicarchitecture.services.AddressSuggestService
import ru.otus.basicarchitecture.services.Suggestion
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class AddressInfoViewModel @Inject constructor(
    private val cache: WizardCache,
    private val service: AddressSuggestService
) : ViewModel() {

    private val entry = MutableSharedFlow<SuggestCommand>()

    val viewState: StateFlow<AddressInfoViewState> get() =
        combine(
            cache.addressInfo,
            entry
                .debounce{
                    when(it){
                        is SuggestCommand.Search -> 1L.seconds
                        is SuggestCommand.Clear -> 0L.seconds
                    }
                }
                .map {
                    when(it){
                        is SuggestCommand.Search -> service.suggest(it.address)
                        is SuggestCommand.Clear -> emptyList()
                    }
                }
        ) { data, addressList ->
            addAddressInfo(data, addressList.orEmpty())
        }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                addAddressInfo(cache.addressInfo.value, emptyList())
            )

    private fun addAddressInfo(
        data: AddressInformationData,
        addressList: List<Suggestion>
    ) = AddressInfoViewState(
        data.address,
        addressList)

    private fun isValid(text: String) = text.isNotEmpty()

    fun setAddress(address: String) {
        cache.setAddress(address)
        viewModelScope.launch {
            entry.emit(SuggestCommand.Clear)
        }
    }

    fun searchAddress(address: String) {
        setAddress(address)
        viewModelScope.launch {
            entry.emit(SuggestCommand.Search(address))
        }
    }
}

data class AddressInfoViewState(
    val address: String,
    val addressList: List<Suggestion>
)

sealed class SuggestCommand {
    data class Search(val address: String) : SuggestCommand()
    data object Clear : SuggestCommand()
}