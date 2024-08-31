package ru.otus.basicarchitecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.cache.Interests
import ru.otus.basicarchitecture.cache.WizardCache
import javax.inject.Inject

@HiltViewModel
class InterestViewModel @Inject constructor(
private val wizardCache: WizardCache
) : ViewModel() {
    private val _interests = listOf(
        "Водка", "Колька","Завод", "Деньги", "Много денег", "ОЧЕНЬ МНОГО Денег"
    )

    private val _selectedInterests = MutableLiveData<List<String>>()
    val selectedInterests: LiveData<List<String>> get() = _selectedInterests

    val interests: List<String> get() = _interests

    init {
        _selectedInterests.value = emptyList()
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
        wizardCache.interests = Interests(selectedInterests.value?: emptyList())
    }
}