package ru.otus.basicarchitecture.address

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DadataRepository
import com.example.domain.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.DataCacheStorage
import javax.inject.Inject

@HiltViewModel
class AddressFragmentModel @Inject constructor(private val dataCacheStorage: DataCacheStorage, private val dadataRepository: DadataRepository): ViewModel(){
    private val _state = MutableLiveData<AddressFragmentState>()

    val addressFragmentState: LiveData<AddressFragmentState> = _state

    init {
        _state.postValue(
            AddressFragmentState(
                dataCacheStorage.cache.value?.country ?: "",
                dataCacheStorage.cache.value?.city ?: "",
                dataCacheStorage.cache.value?.address ?: "",
                checkedAssessButton(),
            )
        )
    }

    private fun checkedAssessButton(): Boolean {
        return /*_state.value?.country?.isNotEmpty() == true && _state.value?.city?.isNotEmpty() == true && _state.value?.address?.isNotEmpty() ==*/ true
    }
    fun isButtonEnabled(): Boolean {
        return _state.value?.accessNextButton ?: false
    }

    fun onNextButtonClicked() {
        dataCacheStorage.cache.value = dataCacheStorage.cache.value?.copy(
            country = _state.value?.country.toString(),
            city = _state.value?.city.toString(),
            address = _state.value?.address.toString()
        )
    }

    fun checkTextFields(countryText: Editable?, cityText: Editable?, addressText: Editable?) {
        val currentState = _state.value ?: AddressFragmentState()
        _state.postValue(
            currentState.copy(
                country = countryText.toString(),
                city = cityText.toString(),
                address = addressText.toString(),
                accessNextButton = checkedAssessButton(),
            )
        )
    }
    fun getAddressSuggestions(query: String) {
        viewModelScope.launch {
            dadataRepository.getAddressSuggestions(query).collect { viewState ->
                when (viewState) {
                    is ViewState.Loading -> {
                        // Handle loading state
                    }
                    is ViewState.Success -> {
                        // Handle success state
                        val addressSuggestions = viewState.data
                        // Use address suggestions
                    }
                    is ViewState.Error -> {
                        // Handle error state
                        val exception = viewState.exception
                        // Handle exception
                    }
                }
            }
        }
    }

}