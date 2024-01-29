package ru.otus.basicarchitecture.wizardCache

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import javax.inject.Inject

@ActivityRetainedScoped
class WizardCache @Inject constructor() {
    private val _state: MutableStateFlow<RegistrationData> = MutableStateFlow(RegistrationData())

    val state: StateFlow<RegistrationData> get() = _state.asStateFlow()

    fun setName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun setSurname(surname: String) {
        _state.value = _state.value.copy(surname = surname)
    }

    fun setBirthday(birthday: Date) {
        _state.value = _state.value.copy(birthday = birthday)
    }

    fun setAddress(address: String) {
        _state.value = _state.value.copy(address = address)
    }

    fun setInterests(interests: String) {
        _state.value =
            _state.value.copy(selectedInterests = _state.value.selectedInterests.plus(interests))
    }

    fun removeInterests(interests: String) {
        _state.value =
            _state.value.copy(selectedInterests = _state.value.selectedInterests.minus(interests))
    }
}

data class RegistrationData(
    val name: String = "",
    val surname: String = "",
    val birthday: Date = Date(),
    val address: String = "",
    val selectedInterests: Set<String> = emptySet(),
) {
    val listOfInterests: Set<String> =
        setOf("Music", "Cocking", "Walking", "Working", "Picture", "Travels", "Film", "Series")
}