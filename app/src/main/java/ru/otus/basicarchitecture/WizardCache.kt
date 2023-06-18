package ru.otus.basicarchitecture

import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@ActivityRetainedScoped
class WizardCache @Inject constructor() {
    lateinit var name:String
    lateinit var surname:String
    lateinit var date:LocalDate
    lateinit var interests: List<String>
    lateinit var address:String

    fun saveNameData(name: String, surname:String, date: LocalDate) {
        this.name = name
        this.surname = surname
        this.date = date
    }

    fun saveInterests(interests: List<String>) {
        this.interests = interests
    }

    fun saveAddressData(address: String) {
        this.address = address
    }
}