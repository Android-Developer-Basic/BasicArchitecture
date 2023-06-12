package ru.otus.basicarchitecture

import dagger.hilt.android.scopes.ActivityScoped
import java.util.Date
import javax.inject.Inject

@ActivityScoped
class WizardCache @Inject constructor() {
    var name: String = ""
    var surname: String = ""
    var birthdate: Date? = null
    var country: String = ""
    var city: String = ""
    var address: String = ""
    var interests: List<String> = listOf()
}