package ru.otus.basicarchitecture.presentation.interestsFragment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    val selectedInterests = mutableSetOf<String>()

    fun getInterests(): List<String> {
        return listOf("Sports", "Music", "Travel", "Books", "Movies", "Tech", "Gaming")
    }

    fun saveData(): Boolean {
        wizardCache.interests = selectedInterests.toList()
        return true
    }
}