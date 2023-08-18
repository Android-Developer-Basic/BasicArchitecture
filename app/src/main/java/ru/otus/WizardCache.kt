package ru.otus

import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import ru.otus.basicarchitecture.internetAdrdess.AddressAnswer
import java.time.LocalDate
import javax.inject.Inject


@ActivityRetainedScoped //долго живет(иначе не переживет поворот?)
class WizardCache @Inject constructor() {

    //Fragment1
    var name: String = ""
    var surName: String = ""
    var bd: LocalDate? = null

//*************************************

    //Fragment2
    var country: String = ""
    var variantCountry: String = ""


    var TEST: String = ""
//    var city: String = ""
//    var address: String = ""

//*************************************

    //Fragment3
    var tags: Map<String, Boolean> = mapOf(
        "Kotlin" to false,
        "Android" to false,
        "Swift" to false,
        "Ios" to false,
        "C/C++" to false,
        "Java" to false,
        "Ruby" to false,

        )
}
