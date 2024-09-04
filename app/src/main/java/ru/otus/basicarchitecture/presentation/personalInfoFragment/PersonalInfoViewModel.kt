package ru.otus.basicarchitecture.presentation.personalInfoFragment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.repository.WizardCache
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {
    var name: String? = null
    var surname: String? = null
    var dateOfBirth: String? = null

    fun validateAndProceed(): Boolean {
        return if (validateAge(dateOfBirth)) {
            wizardCache.name = name
            wizardCache.surname = surname
            wizardCache.dateOfBirth = dateOfBirth
            true
        } else {
            false
        }
    }

    private fun validateAge(date: String?): Boolean {
        if (date.isNullOrBlank()) return false

        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        dateFormat.isLenient = false // Строгая проверка даты

        return try {
            val birthDate = dateFormat.parse(date) ?: return false
            val today = Calendar.getInstance().time
            val eighteenYearsLater = Calendar.getInstance().apply {
                time = birthDate
                add(Calendar.YEAR, 18)
            }.time

            !eighteenYearsLater.after(today) // Дата рождения должна быть такой, что спустя 18 лет не позже текущей даты
        } catch (e: ParseException) {
            false // Если формат даты неверен, возвращаем false
        }
    }
}