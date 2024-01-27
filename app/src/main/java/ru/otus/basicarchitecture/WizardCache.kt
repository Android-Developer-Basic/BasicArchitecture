package ru.otus.basicarchitecture

import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.time.LocalDate
import javax.inject.Inject

@ActivityRetainedScoped
class WizardCache @Inject constructor() {
    var firstName: String = ""
    var lastName: String? = ""
    var birthDate: LocalDate = LocalDate.now().minusYears(18)
    var address: String = ""
    var interests: Set<String> = emptySet()
    var selectedInterests: Set<String> = emptySet()
}