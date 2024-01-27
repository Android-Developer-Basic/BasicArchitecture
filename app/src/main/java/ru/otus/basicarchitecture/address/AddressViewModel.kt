package ru.otus.basicarchitecture.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.otus.basicarchitecture.WizardCache
import ru.otus.basicarchitecture.address.suggestions.SuggestionsItem
import ru.otus.basicarchitecture.databinding.AddressFragmentBinding
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val useCase: AddressUseCase,
    private val cache: WizardCache
) : ViewModel() {

    private val mSuggestionsGroupState =
        MutableLiveData<SuggestionsGroupState>(SuggestionsGroupState.NotSet)
    val suggestionsGroupState: LiveData<SuggestionsGroupState> get() = mSuggestionsGroupState

    private val mSuggestionsState = MutableLiveData<List<SuggestionsItem>>()
    val suggestionsState: LiveData<List<SuggestionsItem>> get() = mSuggestionsState

    private var loadingSuggestionsTask: Job = Job()

    fun loadSuggestions(input: String) {
        loadingSuggestionsTask.cancel()
        loadingSuggestionsTask = viewModelScope.launch {
            mSuggestionsGroupState.value = SuggestionsGroupState.Loading
            try {
                withContext(Dispatchers.IO) { useCase.getSuggestions(input) }
                    .takeIf { it.isSuccess }
                    ?.let {
                        mSuggestionsState.value =
                            it.getOrNull()
                                ?.suggestions
                                ?.filter { s -> s.value != input }
                                ?.mapNotNull { s -> s.value?.let { v -> SuggestionsItem(v) } }
                                ?: emptyList()
                        mSuggestionsGroupState.value = SuggestionsGroupState.Content
                    } ?: let {
                        mSuggestionsGroupState.value = SuggestionsGroupState.Error
                    }
            } catch (t: Throwable) {
                mSuggestionsGroupState.value = SuggestionsGroupState.Error
            }
        }
    }

    fun fillFieldsFromCache(binding: AddressFragmentBinding) {
        binding.addressField.setText(cache.address)
    }

    fun saveFieldsToCache(binding: AddressFragmentBinding) {
        cache.address = binding.addressField.text?.toString() ?: ""
    }

    sealed class SuggestionsGroupState {

        data object NotSet: SuggestionsGroupState()

        data object Loading: SuggestionsGroupState()

        data object Content: SuggestionsGroupState()

        data object Error: SuggestionsGroupState()
    }

}