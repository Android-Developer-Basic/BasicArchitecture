package ru.otus.basicarchitecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.model.Tag
import ru.otus.basicarchitecture.repository.TagRepository
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Inject
@HiltViewModel
class Fragment1ViewModel @Inject constructor(private val wizardCache: WizardCache) : ViewModel() {

    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val birthDate = MutableLiveData<String>()

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
        // Логика проверки возраста (например, 18+)
        return true // Замените на реальную логику
    }
}