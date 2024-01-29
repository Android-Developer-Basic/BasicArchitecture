package ru.otus.basicarchitecture.personal_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.otus.basicarchitecture.data.PersonalInformationData
import ru.otus.basicarchitecture.data.WizardCache
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val cache: WizardCache
) : ViewModel() {

    val viewState: StateFlow<PersonalInfoViewState> = cache.personalInfo
        .map { addPersonalInfo(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, addPersonalInfo(cache.personalInfo.value))

    fun setName(name: String) {
        cache.setName(name)
    }

    fun setSurname(surname: String) {
        cache.setSurname(surname)
    }

    fun setDateOfBirth(year: Int, month: Int, dayOfMonth: Int) {
        val dateOfBirth = String.format("%02d.%02d.%04d", dayOfMonth, month + 1, year)
        cache.setDateOfBirth(dateOfBirth)
    }

    private fun addPersonalInfo(
        data: PersonalInformationData
    ) = PersonalInfoViewState(
        data.name,
        data.surname,
        data.dateOfBirth,
        isValidNameOrSurname(data.name),
        isValidNameOrSurname(data.surname),
        isValidDateOfBirth(data.dateOfBirth))

    private fun isValidNameOrSurname(name: String) = name.length > 2
    private fun isValidDateOfBirth(dateOfBirth: String): Boolean {

        if (dateOfBirth.isEmpty()) return false

        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val currentDate = Calendar.getInstance().time
        val currentDateCalendar = Calendar.getInstance()
        currentDateCalendar.time = currentDate

        val selectedDate = dateFormat.parse(dateOfBirth)
        val selectedDateCalendar = Calendar.getInstance()
        selectedDateCalendar.time = selectedDate

        selectedDateCalendar.add(Calendar.YEAR, 18)

        return selectedDateCalendar.before(currentDateCalendar)
    }
}

data class PersonalInfoViewState(
    val name: String,
    val surname: String,
    val dateOfBirth: String,
    val isValidName: Boolean,
    val isValidSurname: Boolean,
    val isValidDateOfBirth: Boolean
)