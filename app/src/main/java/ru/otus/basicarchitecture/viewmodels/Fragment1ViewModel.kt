package ru.otus.basicarchitecture.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.repository.WizardCache
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
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
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val today = LocalDate.now()

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

    fun validateInput(): Boolean {
        // Валидация имени, фамилии и возраста
        return !firstName.value.isNullOrEmpty() && !lastName.value.isNullOrEmpty() && isAgeValid(birthDate.value)
    }

    private fun isAgeValid(birthDate: String?): Boolean {
        if (birthDate.isNullOrEmpty()) return false

        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return try {
            val birthDateParsed = dateFormat.parse(birthDate)
            val age = Calendar.getInstance().get(Calendar.YEAR) - Calendar.getInstance().apply {
                if (birthDateParsed != null) {
                    time = birthDateParsed
                }
            }.get(Calendar.YEAR)

            age >= 18
        } catch (e: ParseException) {
            false
        }
    }

}