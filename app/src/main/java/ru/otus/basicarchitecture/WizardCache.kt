package ru.otus.basicarchitecture

import dagger.hilt.android.scopes.ActivityRetainedScoped
//import java.util.Date
import javax.inject.Inject
import java.time.LocalDate

@ActivityRetainedScoped
class WizardCache @Inject constructor() {

    var name: String = ""
    var surname: String = ""
    lateinit var date:LocalDate
    var country: String = ""
    var city: String = ""
    var address: String = ""
    var interests: List<String> = listOf()

    fun saveNameData(name: String, surname:String, date: LocalDate) {
        this.name = name
        this.surname = surname
        this.date = date
    }

    fun saveInterests(interests: List<String>) {
        this.interests = interests
    }

    fun saveAddressData(country: String, city:String, address: String) {
        this.country = country
        this.city = city
        this.address = address
    }

}
