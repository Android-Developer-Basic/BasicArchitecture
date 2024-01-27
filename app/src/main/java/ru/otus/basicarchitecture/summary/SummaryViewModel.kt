package ru.otus.basicarchitecture.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCache
import ru.otus.basicarchitecture.databinding.SummaryFragmentBinding
import ru.otus.basicarchitecture.toBrithDateString
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val useCase: SummaryUseCase,
    private val cache: WizardCache
) : ViewModel() {

    private val mSelectedInterestsState = MutableLiveData<Set<String>>()
    val selectedInterestsState: LiveData<Set<String>> get() = mSelectedInterestsState

    fun fillFieldsFromCache(binding: SummaryFragmentBinding) {
        binding.firstName.text = cache.firstName
        binding.lastName.text = cache.lastName
        binding.birthDate.text = cache.birthDate.toBrithDateString()
        binding.address.text = cache.address
        mSelectedInterestsState.value = cache.selectedInterests
    }

}