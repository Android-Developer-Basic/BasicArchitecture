package ru.otus.basicarchitecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Inject

@HiltViewModel
class Fragment3ViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    private val _interests = listOf(
        "Sport", "Music", "Travel", "Reading", "Movies", "Games", "Cooking", "Art"
    )

    private val _selectedInterests = MutableLiveData<Set<String>>()
    val selectedInterests: LiveData<Set<String>> get() = _selectedInterests

    val interests: List<String> get() = _interests

    init {
        _selectedInterests.value = emptySet()
    }

    fun toggleInterest(interest: String) {
        _selectedInterests.value = _selectedInterests.value?.let {
            if (it.contains(interest)) {
                it - interest
            } else {
                it + interest
            }
        }
    }

    fun saveSelectedInterests() {
        _selectedInterests.value?.let {
            wizardCache.setInterests(it)
        }
    }
}