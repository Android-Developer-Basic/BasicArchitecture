package ru.otus.basicarchitecture.personal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.WizardCache
import ru.otus.basicarchitecture.databinding.PersonalDataFragmentBinding
import ru.otus.basicarchitecture.toBirthDate
import ru.otus.basicarchitecture.toBrithDateString
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class PersonalDataViewModel @Inject constructor(
    private val useCase: PersonalDataUseCase,
    private val cache: WizardCache
) : ViewModel() {

    private val mNextButtonState = MutableLiveData(false)
    val nextButtonState: LiveData<Boolean> get() = mNextButtonState

    fun updateNextButtonStatus(date: String?) {
        try {
            mNextButtonState.value =
                date?.let { useCase.isBirthDateValid(it.toBirthDate())
                } ?: false
        } catch (e: DateTimeParseException) {
            mNextButtonState.value = false
        }
    }

    fun fillFieldsFromCache(binding: PersonalDataFragmentBinding) {
        binding.firstNameField.setText(cache.firstName)
        binding.lastNameField.setText(cache.lastName)
        binding.dateField.setText(
            cache.birthDate.toBrithDateString())
    }

    fun saveFieldsToCache(binding: PersonalDataFragmentBinding) {
        cache.firstName = binding.firstNameField.text?.toString() ?: ""
        cache.lastName = binding.lastNameField.text?.toString() ?: ""
        try {
            binding.dateField.text?.apply {
                cache.birthDate = toBirthDate()
            }
        } catch (_: DateTimeParseException) {}
    }

}