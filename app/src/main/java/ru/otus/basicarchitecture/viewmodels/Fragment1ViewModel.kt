package ru.otus.basicarchitecture.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.repository.WizardCache
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class Fragment1ViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {

    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val birthDate = MutableLiveData<String>()

    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> get() = _isFormValid

    init {
        firstName.observeForever { validateForm() }
        lastName.observeForever { validateForm() }
        birthDate.observeForever { validateForm() }
    }

    private fun validateForm() {
        val isNameValid = !firstName.value.isNullOrBlank()
        val isSurnameValid = !lastName.value.isNullOrBlank()
        val isDateOfBirthValid = birthDate.value?.let {
            try {
                val dob = LocalDate.parse(it, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                val years = ChronoUnit.YEARS.between(dob, LocalDate.now())
                years >= 18
            } catch (e: Exception) {
                false
            }
        } ?: false

        _isFormValid.value = isNameValid && isSurnameValid && isDateOfBirthValid
    }

    fun saveData() {
        wizardCache.firstName = firstName.value
        wizardCache.lastName = lastName.value
        wizardCache.birthDate = birthDate.value
    }

}