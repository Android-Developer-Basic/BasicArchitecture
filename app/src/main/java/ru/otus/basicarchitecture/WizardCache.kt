package ru.otus.basicarchitecture

import dagger.hilt.android.scopes.ActivityScoped
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WizardCache @Inject constructor() {
    lateinit var name:String
    lateinit var surname:String
    lateinit var date:LocalDate
    lateinit var interests: List<String>
    lateinit var country:String
    lateinit var city:String
    lateinit var address:String

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