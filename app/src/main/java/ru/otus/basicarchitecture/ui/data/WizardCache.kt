package ru.otus.basicarchitecture.ui.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WizardCache @Inject constructor() {

    var personName: String = ""
    var personSurname: String = ""
    var personBirthDate: String = ""

    var personAddress: String = ""

    var hobby: MutableList<String> = mutableListOf()
}