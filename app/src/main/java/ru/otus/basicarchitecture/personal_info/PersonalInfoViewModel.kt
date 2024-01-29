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
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    private val cache: WizardCache.Base
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

    fun setDateOfBirth(dateOfBirth: String) {
        cache.setDateOfBirth(dateOfBirth)
    }

    private fun addPersonalInfo(
        data: PersonalInformationData
    ) = PersonalInfoViewState(
        data.name,
        data.surname,
        data.dateOfBirth,
        isValidNameOrSurname(data.name) &&
                isValidNameOrSurname(data.surname) &&
                isValidDateOfBirth(data.dateOfBirth) )

    private fun isValidNameOrSurname(name: String) = name.length > 2
    private fun isValidDateOfBirth(dateOfBirth: String) = dateOfBirth.length > 2
}

data class PersonalInfoViewState(
    val name: String,
    val surname: String,
    val dateOfBirth: String,
    val nextEnabled: Boolean
)