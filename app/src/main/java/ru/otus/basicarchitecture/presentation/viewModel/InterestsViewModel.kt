package ru.otus.basicarchitecture.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.data.InterestsList
import ru.otus.basicarchitecture.data.WizardCache
import javax.inject.Inject

class InterestsViewModel @Inject constructor(
    private val wizardCache: WizardCache, interestsList: InterestsList
) : ViewModel() {

    private val _interests = MutableLiveData(interestsList.interests)
    val interests: LiveData<List<String>> = _interests

    private val _selectedInterests = MutableLiveData<MutableList<String>>()
    val selectedInterests: LiveData<MutableList<String>> = _selectedInterests

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