package ru.otus.basicarchitecture.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DaDataRepository
import com.example.domain.data.Address
import com.example.net.DaDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.wizardCache.RegistrationData
import ru.otus.basicarchitecture.wizardCache.WizardCache
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class AddressFragmentModel @Inject constructor(
    private val cache: WizardCache,
    private val service: DaDataRepository,
) : ViewModel() {

    private val entry = MutableSharedFlow<SuggestCommand>()

    @OptIn(FlowPreview::class)
    val viewState: StateFlow<AddressFragmentViewState>
        get() =
            combine(
                cache.state,
                entry
                    .debounce {
                        when (it) {
                            is SuggestCommand.Search -> 1L.seconds
                            is SuggestCommand.Clear -> 0L.seconds
                        }
                    }
                    .map {
                        when (it) {
                            is SuggestCommand.Search -> service.getAddressSuggestions(it.address)
                            is SuggestCommand.Clear -> emptyList()
                        }
                    }
            ) { data, addresses ->
                render(data, addresses)
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                render(cache.state.value, emptyList())
            )

    /**
     * Sets address
     */
    fun setAddress(address: String) {
        cache.setAddress(address)
        viewModelScope.launch {
            entry.emit(SuggestCommand.Clear)
        }
    }

    /**
     * Searches for address
     */
    fun searchAddress(address: String) {
        setAddress(address)
        viewModelScope.launch {
            entry.emit(SuggestCommand.Search(address))
        }
    }

    private fun checkedAssessButton(data: RegistrationData): Boolean {
        return data.address.length > 2
    }

    private fun render(data: RegistrationData, addresses: List<Address>) =
        AddressFragmentViewState(data.address, addresses, checkedAssessButton(data))
}

/**
 * Commands for [AddressViewModel] address suggestions
 */
sealed class SuggestCommand {
    data class Search(val address: String) : SuggestCommand()
    data object Clear : SuggestCommand()
}

@Module
@InstallIn(ViewModelComponent::class)
interface AddressViewModelModule {
    @Binds
    @ViewModelScoped
    fun service(impl: DaDataRepositoryImpl): DaDataRepository
}