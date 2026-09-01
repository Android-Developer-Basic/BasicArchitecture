package ru.otus.basicarchitecture.ui.interests

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
import ru.otus.basicarchitecture.interests.InterestsRepository

data class InterestsUiState(
    val availableInterests: List<String> = emptyList(),
    val selectedInterests: Set<String> = emptySet(),
) {
    val isNextEnabled: Boolean
        get() = selectedInterests.isNotEmpty()
}

class InterestsViewModel @Inject constructor(
    private val wizardCache: WizardCache,
    interestsRepository: InterestsRepository,
) : ViewModel() {
    private val mutableUiState = MutableStateFlow(
        InterestsUiState(
            availableInterests = interestsRepository.getInterests(),
            selectedInterests = wizardCache.data.value.interests,
        ),
    )
    val uiState: StateFlow<InterestsUiState> = mutableUiState.asStateFlow()

    private val mutableNavigateNext = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateNext: SharedFlow<Unit> = mutableNavigateNext.asSharedFlow()

    fun onInterestClicked(interest: String) {
        mutableUiState.update { state ->
            val updated = state.selectedInterests.toMutableSet().apply {
                if (!add(interest)) remove(interest)
            }
            state.copy(selectedInterests = updated)
        }
    }

    fun onNextClicked() {
        val selectedInterests = mutableUiState.value.selectedInterests
        if (selectedInterests.isEmpty()) return

        wizardCache.saveInterests(selectedInterests)
        mutableNavigateNext.tryEmit(Unit)
    }
}
