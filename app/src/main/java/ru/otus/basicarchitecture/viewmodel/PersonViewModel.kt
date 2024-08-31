package ru.otus.basicarchitecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.cache.Person
import ru.otus.basicarchitecture.cache.WizardCache
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {

    private val _state = MutableLiveData<Person>()
    val state: LiveData<Person> get() = _state

    init {
        _state.value = wizardCache.person?:Person()
    }

    fun validateForm():Boolean {
        val isNameValid = !state.value?.name.isNullOrBlank()
        val isSurnameValid = !state.value?.surname.isNullOrBlank()
        val isDateOfBirthValid = state.value?.dateOfBirth.let {
            try {
                val dob = LocalDate.parse(it, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                val years = Period.between(dob,LocalDate.now()).years
                years >= 18
            } catch (e: Exception) {
                false
            }
        }

       return isNameValid && isSurnameValid && isDateOfBirthValid
    }

    fun saveData() {
        wizardCache.person = _state.value
    }

    fun updateName(name : String){
        val currentState = _state.value ?: Person()
        _state.setValue(currentState.copy(name =name))
        saveData()
    }

    fun updateSurname(surname : String){
        val currentState = _state.value ?: Person()
        _state.value=currentState.copy(surname =surname)
        saveData()
    }

    fun updateDateOfBirth(dateOfBirth : String){
        val currentState = _state.value ?: Person()
        _state.setValue(currentState.copy(dateOfBirth = dateOfBirth))
        saveData()
    }

}