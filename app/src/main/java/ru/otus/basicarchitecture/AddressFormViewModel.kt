package ru.otus.basicarchitecture

import javax.inject.Inject

class AddressFormViewModel @Inject constructor(
    private val wizardCache: WizardCache
) {
    fun save(country: String, city: String, address: String) {
        wizardCache.country = country
        wizardCache.city = city
        wizardCache.address = address
    }
}