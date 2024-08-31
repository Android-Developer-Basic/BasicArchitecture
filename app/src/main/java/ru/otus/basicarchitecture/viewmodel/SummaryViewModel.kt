package ru.otus.basicarchitecture.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.cache.WizardCache
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val wizardCache: WizardCache
) : ViewModel() {


    val firstName: String? get() = wizardCache.person?.name
    val lastName: String? get() = wizardCache.person?.surname
    val birthDate: String? get() = wizardCache.person?.dateOfBirth
    val country: String? get() = wizardCache.address?.country
    val city: String? get() = wizardCache.address?.city
    val address: String? get() = wizardCache.address?.address
    val interests: List<String>? get() = wizardCache.interests?.selectedInterest

    val fullAddress: String
        get() = listOfNotNull(country, city, address).joinToString(" ")
}