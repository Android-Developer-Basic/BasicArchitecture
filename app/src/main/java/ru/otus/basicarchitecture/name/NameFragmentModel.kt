package ru.otus.basicarchitecture.name

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.otus.basicarchitecture.wizardCache.RegistrationData
import ru.otus.basicarchitecture.wizardCache.WizardCache
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

/**
 * View-model for [NameFragment]
 */
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NameFragmentModel @Inject constructor(private val cache: WizardCache) :
    ViewModel() {
    val viewState: StateFlow<NameFragmentViewState> = cache.state.map { render(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, render(cache.state.value))

    fun setName(name: String) {
        cache.setName(name)
    }

    fun setSurname(surname: String) {
        cache.setSurname(surname)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setBirthday(birthday: Date) {
        cache.setBirthday(birthday)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkIsAdult(birthday: Date): Boolean {
        val birthdayDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val todayDate = LocalDate.now()
        return Period.between(birthdayDate, todayDate).years >= 18
    }

    private fun checkedAssessButton(data: RegistrationData): Boolean {
        return data.name.length > 2 && data.surname.length > 2 && checkIsAdult(data.birthday)
    }

    private fun render(data: RegistrationData) =
        NameFragmentViewState(
            data.name,
            data.surname,
            data.birthday,
            checkedAssessButton(data)
        )
}