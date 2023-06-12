package ru.otus.basicarchitecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class InterestsViewModel @Inject constructor(
    private val wizardCache: WizardCache,
    interestsSource: InterestsSource
) {
    private val _interests = MutableLiveData(interestsSource.interests)
    val interests: LiveData<List<String>> = _interests

    private val _selectedInterests = MutableLiveData<List<String>>()
    val selectedInterests: LiveData<List<String>> = _selectedInterests

    fun addOrRemoveInterest(interest: String) {
        val interests = _selectedInterests.value?.toMutableList() ?: mutableListOf()
        if (interests.contains(interest)) {
            interests.remove(interest)
        } else {
            interests.add(interest)
        }
        _selectedInterests.value = interests
    }

    fun save() {
        wizardCache.interests = selectedInterests.value ?: listOf()
    }
}