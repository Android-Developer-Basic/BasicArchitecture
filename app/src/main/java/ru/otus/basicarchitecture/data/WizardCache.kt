package ru.otus.basicarchitecture.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class WizardCache @Inject constructor() {
    var name: String = ""
    var surname: String = ""
    var birthDate: Long = 0
    var city: String = ""
    var country: String = ""
    var address: String = ""
    var interests: List<String> = listOf()
}