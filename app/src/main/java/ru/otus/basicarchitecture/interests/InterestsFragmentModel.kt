package ru.otus.basicarchitecture.interests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.otus.basicarchitecture.wizardCache.RegistrationData
import ru.otus.basicarchitecture.wizardCache.WizardCache
import javax.inject.Inject

private const val MIN_INTERESTS_COUNT = 5

@HiltViewModel
class InterestsFragmentModel @Inject constructor(private val cache: WizardCache) :
    ViewModel() {
    val viewState: StateFlow<InterestsFragmentViewState> = cache.state.map { render(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, render(cache.state.value))

    val listOfInterests by lazy {
        cache.state.value.listOfInterests
    }

    fun setInterest(text: String) {
        cache.setInterests(text)
    }

    fun removeInterest(text: String) {
        cache.removeInterests(text)
    }

    private fun render(data: RegistrationData) =
        InterestsFragmentViewState(
            data.selectedInterests,
            checkInterest(data.selectedInterests.size)
        )

    private fun checkInterest(count: Int): Boolean {
        return count >= MIN_INTERESTS_COUNT
    }
}





