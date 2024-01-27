package ru.otus.basicarchitecture.interests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.chip.Chip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.otus.basicarchitecture.WizardCache
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    private val useCase: InterestsUseCase,
    private val cache: WizardCache
) : ViewModel() {

    private val mInterestsGroupState = MutableLiveData<InterestsGroupState>(InterestsGroupState.NotSet)
    val interestsGroupState: LiveData<InterestsGroupState> get() = mInterestsGroupState

    private val mInterestsState = MutableLiveData<Set<String>>()
    val interestsState: LiveData<Set<String>> get() = mInterestsState

    private val mSelectedInterestsState = MutableLiveData<Set<String>>()
    val selectedInterestsState: LiveData<Set<String>> get() = mSelectedInterestsState

    private fun loadAvailableInterests() =
        viewModelScope.launch {
            mInterestsGroupState.value = InterestsGroupState.Loading
            try {
                cache.interests = withContext(Dispatchers.IO) {
                    useCase.getAvailableInterests()
                }
                mInterestsState.value = cache.interests
                mInterestsGroupState.value = InterestsGroupState.Content
            } catch (t: Throwable) {
                mInterestsGroupState.value = InterestsGroupState.Error
            }
        }

    fun fillInterestsFromCache() {
        cache.interests
            .takeIf { it.isEmpty() }
            ?.let { loadAvailableInterests() }
            ?: let {
                mInterestsState.value = cache.interests
                mInterestsGroupState.value = InterestsGroupState.Content
            }
        mSelectedInterestsState.value = cache.selectedInterests
    }

    fun saveInterestsToCache() {
        cache.selectedInterests = mSelectedInterestsState.value ?: emptySet()
    }

    fun processInterestClick(name: String, state: Boolean) =
        if (state) {
            mSelectedInterestsState.value = (mSelectedInterestsState.value ?: emptySet()) + name
        } else {
            mSelectedInterestsState.value = (mSelectedInterestsState.value ?: emptySet()) - name
        }

    sealed class InterestsGroupState {

        data object NotSet: InterestsGroupState()

        data object Loading: InterestsGroupState()

        data object Content: InterestsGroupState()

        data object Error: InterestsGroupState()
    }

}