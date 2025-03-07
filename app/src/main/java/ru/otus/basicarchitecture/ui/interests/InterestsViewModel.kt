package ru.otus.basicarchitecture.ui.interests

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.ui.data.WizardCache
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    var wizardCache: WizardCache
) : ViewModel() {

    fun addHobby(hobby: String) {
        wizardCache.hobby.add(hobby)
    }

    fun removeHobby(hobby: String) {
        wizardCache.hobby.remove(hobby)
    }

    fun onBack() {
        wizardCache.hobby.clear()
    }
}