package ru.otus.basicarchitecture.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.domain.WizardCacheService
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    private val wizardCacheService: WizardCacheService
) : ViewModel() {

    fun isAgeValid(age: Int?): Boolean {
        return age != null && age >= 18
    }

    fun updateUserInfo(name: String, surname: String, age: Int?) {
        wizardCacheService.updateUserPersonalInformation(
            name = name,
            surname = surname,
            age = age
        )
    }
}