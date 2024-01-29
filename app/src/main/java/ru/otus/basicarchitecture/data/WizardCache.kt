package ru.otus.basicarchitecture.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


interface PersonalInformation {
    val personalInfo: StateFlow<PersonalInformationData>
    fun setName(name: String)
    fun setSurname(surname: String)
    fun setDateOfBirth(dateOfBirth: String)
}

interface AddressInformation {
    val addressInfo: StateFlow<AddressInformationData>
    fun setCountry(country: String)
    fun setCity(city: String)
    fun setAddress(address: String)
}

@ActivityRetainedScoped
class WizardCache @Inject constructor() : PersonalInformation, AddressInformation {
    private val _personalInfo: MutableStateFlow<PersonalInformationData> =
        MutableStateFlow(PersonalInformationData())
    override val personalInfo: StateFlow<PersonalInformationData> get() = _personalInfo.asStateFlow()


    private val _addressInfo: MutableStateFlow<AddressInformationData> =
        MutableStateFlow(AddressInformationData())
    override val addressInfo: StateFlow<AddressInformationData> get() = _addressInfo.asStateFlow()

    override fun setName(name: String) {
        _personalInfo.value = _personalInfo.value.copy(name = name)
    }

    override fun setSurname(surname: String) {
        _personalInfo.value = _personalInfo.value.copy(surname = surname)
    }

    override fun setDateOfBirth(dateOfBirth: String) {
        _personalInfo.value = _personalInfo.value.copy(dateOfBirth = dateOfBirth)
    }

    override fun setCountry(country: String) {
        _addressInfo.value = _addressInfo.value.copy(country = country)
    }

    override fun setCity(city: String) {
        _addressInfo.value = _addressInfo.value.copy(city = city)
    }

    override fun setAddress(address: String) {
        _addressInfo.value = _addressInfo.value.copy(address = address)
    }
}

data class PersonalInformationData(val name: String = "", val surname: String = "", val dateOfBirth: String = "")
data class AddressInformationData(val country: String = "", val city: String = "", val address: String = "")