package ru.otus.basicarchitecture.cache

import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WizardCache  @Inject constructor(){

   var person:Person?=null
   var address:Address?=null
   var interests:Interests?=null

}

data class Person (val name: String = "test", val surname: String = "test", val dateOfBirth: String = "01.01.1970")
data class Address(val country: String = "", val city: String = "", val address: String = "")
data class Interests(val selectedInterest: List<String> = emptyList<String>())