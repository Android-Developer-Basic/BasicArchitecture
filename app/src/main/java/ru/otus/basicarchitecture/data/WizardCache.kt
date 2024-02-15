package ru.otus.basicarchitecture.data

import ru.otus.basicarchitecture.domain.Model.Address
import ru.otus.basicarchitecture.domain.Model.Interests
import ru.otus.basicarchitecture.domain.Model.Person
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class WizardCache(
    //person
    var firstName: String,
    var surName: String,
    var dateOfBirth: String,

    //address
    var country: String,
    var city: String,
    var address: String,

    //interests
    var interests:  String
)