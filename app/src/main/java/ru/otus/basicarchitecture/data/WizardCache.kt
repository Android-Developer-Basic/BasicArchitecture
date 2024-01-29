package ru.otus.basicarchitecture.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface WizardCache {
    fun setName(name: String)
    fun setSurname(surname: String)
    fun setDateOfBirth(dateOfBirth: String)

    @ActivityRetainedScoped
    class Base @Inject constructor() : WizardCache {
        private val _personalInfo: MutableStateFlow<PersonalInformationData> = MutableStateFlow(PersonalInformationData())
        val personalInfo: StateFlow<PersonalInformationData> get() = _personalInfo.asStateFlow()

        override fun setName(name: String) {
            _personalInfo.value = _personalInfo.value.copy(name = name)
        }

        override fun setSurname(surname: String) {
            _personalInfo.value = _personalInfo.value.copy(surname = surname)
        }

        override fun setDateOfBirth(dateOfBirth: String) {
            _personalInfo.value = _personalInfo.value.copy(dateOfBirth = dateOfBirth)
        }
    }
}

data class PersonalInformationData(val name: String = "", val surname: String = "", val dateOfBirth: String = "")