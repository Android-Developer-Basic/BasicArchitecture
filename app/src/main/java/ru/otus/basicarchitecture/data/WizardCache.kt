package ru.otus.basicarchitecture.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.util.Date
import javax.inject.Inject

@ActivityRetainedScoped
class WizardCache @Inject constructor() {
    var name: String = ""
    var surname: String = ""
    var birthdate: Date? = null
    var address: String = ""
    var interests: List<String> = listOf()
}