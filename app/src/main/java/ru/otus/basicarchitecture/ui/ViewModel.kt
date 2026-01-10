package ru.otus.basicarchitecture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.data.WizardCache
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject

// StepOneViewModel
// region
@HiltViewModel
class StepOneViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    val name = MutableStateFlow("")
    val surname = MutableStateFlow("")
    val birthDate = MutableStateFlow("")

    private val _isNextButtonEnabled = MutableStateFlow(false)
    val isNextButtonEnabled: StateFlow<Boolean> = _isNextButtonEnabled

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents = _errorEvents.asSharedFlow()

    fun onInputChanged(newName: String, newSurname: String, newBirthDate: String) {
        name.value = newName
        surname.value = newSurname
        birthDate.value = newBirthDate
        validate()
    }

    private fun validate() {
        val currentName = name.value
        val currentSurname = surname.value
        val currentDate = birthDate.value

        val isDateValidLength = currentDate.length == 10
        val isAdult = if (isDateValidLength) checkIsAdult(currentDate) else false

        val isValid = currentName.isNotBlank() && currentSurname.isNotBlank() && isAdult
        _isNextButtonEnabled.value = isValid

        if (isDateValidLength && !isAdult) {
            viewModelScope.launch { _errorEvents.emit("Вам должно быть больше 18 лет") }
        }
    }

    fun onNextClicked() {
        if (_isNextButtonEnabled.value) {
            wizardCache.updatePersonalInfo(name.value, surname.value, birthDate.value)
        } else {
            viewModelScope.launch { _errorEvents.emit("Заполните все поля корректно") }
        }
    }

    private fun checkIsAdult(birthDateStr: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val date = LocalDate.parse(birthDateStr, formatter)
            Period.between(date, LocalDate.now()).years >= 18
        } catch (_: Exception) {
            false
        }
    }
}
// endregion

// StepTwoViewModel
// region
@HiltViewModel
class StepTwoViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    private val _isNextButtonEnabled = MutableStateFlow(false)
    val isNextButtonEnabled: StateFlow<Boolean> = _isNextButtonEnabled

    fun validate(country: String, city: String, address: String) {
        _isNextButtonEnabled.value =
            country.isNotBlank() && city.isNotBlank() && address.isNotBlank()
    }

    fun onNextClicked(country: String, city: String, address: String) {
        wizardCache.updateAddress(country, city, address)
    }
}
// endregion

// StepThreeViewModel
// region
@HiltViewModel
class StepThreeViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    val allInterests = listOf("Музыка", "Спорт", "Кино", "IT", "Путешествия", "Наука", "Книги")
    private val selectedInterests = mutableSetOf<String>()

    private val _isNextButtonEnabled = MutableStateFlow(false)
    val isNextButtonEnabled: StateFlow<Boolean> = _isNextButtonEnabled

    fun toggleInterest(interest: String) {
        if (selectedInterests.contains(interest)) {
            selectedInterests.remove(interest)
        } else {
            selectedInterests.add(interest)
        }
        _isNextButtonEnabled.value = selectedInterests.isNotEmpty()
    }

    fun onInterestChanged(interest: String, isSelected: Boolean) {
        if (isSelected) {
            selectedInterests.add(interest)
        } else {
            selectedInterests.remove(interest)
        }
        _isNextButtonEnabled.value = selectedInterests.isNotEmpty()
    }

    fun onNextClicked() {
        wizardCache.updateInterests(selectedInterests.toList())
    }

}
// endregion

// StepFourViewModel
// region
@HiltViewModel
class StepFourViewModel @Inject constructor(
    wizardCache: WizardCache
) : ViewModel() {
    val birthDate = wizardCache.birthDate
    val fullAddress = "${wizardCache.country}, ${wizardCache.city}, ${wizardCache.address}"
    val interests = wizardCache.interests
    val name = wizardCache.name
    val surname = wizardCache.surname
}
// endregion