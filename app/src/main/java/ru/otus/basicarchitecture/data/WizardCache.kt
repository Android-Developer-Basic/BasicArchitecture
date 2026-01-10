package ru.otus.basicarchitecture.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WizardCache @Inject constructor() {

    private var _name: String = ""
    val name: String get() = _name

    private var _surname: String = ""
    val surname: String get() = _surname

    private var _birthDate: String = ""
    val birthDate: String get() = _birthDate

    private var _country: String = ""
    val country: String get() = _country

    private var _city: String = ""
    val city: String get() = _city

    private var _address: String = ""
    val address: String get() = _address

    private var _interests: List<String> = emptyList()
    val interests: List<String> get() = _interests

    fun updatePersonalInfo(name: String, surname: String, birthDate: String) {
        _name = name
        _surname = surname
        _birthDate = birthDate
    }

    fun updateAddress(country: String, city: String, address: String) {
        _country = country
        _city = city
        _address = address
    }

    fun updateInterests(interests: List<String>) {
        _interests = interests
    }
}