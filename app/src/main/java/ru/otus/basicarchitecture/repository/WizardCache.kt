package ru.otus.basicarchitecture.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WizardCache @Inject constructor() {
    var name: String? = null
    var surname: String? = null
    var dateOfBirth: String? = null
    var country: String? = null
    var city: String? = null
    var address: String? = null
    var interests: List<String> = emptyList()
}
