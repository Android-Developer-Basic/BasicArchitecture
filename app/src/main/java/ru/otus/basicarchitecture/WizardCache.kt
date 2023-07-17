package ru.otus.basicarchitecture

import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.util.Date
import javax.inject.Inject

@ActivityRetainedScoped
class WizardCache @Inject constructor() {
    var firstName: String? = null
    var lastName: String? = null
    var dateOfBirth: Date? = null
    var country: String? = null
    var city: String? = null
    var address: String? = null
    var selectedTags: MutableList<String> = mutableListOf()
}