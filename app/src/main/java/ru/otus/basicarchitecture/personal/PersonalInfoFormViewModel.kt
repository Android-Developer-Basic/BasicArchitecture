package ru.otus.basicarchitecture.personal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.otus.basicarchitecture.data.WizardCache
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class PersonalInfoFormViewModel @Inject constructor(
    private val wizardCache: WizardCache
) {
    private val dateFormat = SimpleDateFormat("ddMMyy", Locale.getDefault())
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun validateBirthdate(birthdate: String): Boolean {
        try {
            val date = dateFormat.parse(birthdate)!!
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -18)
            if (date.after(calendar.time)) {
                _error.value = "Вам нет 18 лет"
                return false
            }
        } catch (e: Throwable) {
            _error.value = "Не верный формат даты (DD.MM.YY)"
            return false
        }
        return true
    }

    fun clearError() {
        _error.value = null
    }

    fun save(name: String, surname: String, birthdate: String) {
        wizardCache.name = name
        wizardCache.surname = surname
        try {
            wizardCache.birthdate = dateFormat.parse(birthdate)
        } catch (ignored: Throwable) {}
    }
}