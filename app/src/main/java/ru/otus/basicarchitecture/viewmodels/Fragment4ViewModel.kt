package ru.otus.basicarchitecture.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.repository.WizardCache
import javax.inject.Inject

@HiltViewModel
class Fragment4ViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {

    val firstName: String? get() = wizardCache.firstName
    val lastName: String? get() = wizardCache.lastName
    val birthDate: String? get() = wizardCache.birthDate
    val country: String? get() = wizardCache.country
    val city: String? get() = wizardCache.city
    val address: String? get() = wizardCache.address
    val interests: List<String> get() = wizardCache.interests

    val fullAddress: String
        get() = listOfNotNull(country, city, address).joinToString(" ")
}
