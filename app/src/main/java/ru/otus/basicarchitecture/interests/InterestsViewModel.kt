package ru.otus.basicarchitecture.interests

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.otus.basicarchitecture.data.WizardCache
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    private val cache: WizardCache
) : ViewModel() {

    private val _viewState = MutableStateFlow(false)
    val viewState: StateFlow<Boolean> get() = _viewState

    val defaultInterests = arrayOf(
        "Music", "Travel", "Photography", "Cooking", "Sports",
        "Reading", "Gaming", "Art", "Hiking", "Dancing")

    fun getInterestChecked(interest: String) : Boolean {
        return cache.interestInformation.value.selectedInterest.contains(interest)
    }

    fun updateInterest(interest: String){
        cache.updateInterest(interest)
        _viewState.value = cache.interestInformation.value.selectedInterest.isNotEmpty()
    }
}