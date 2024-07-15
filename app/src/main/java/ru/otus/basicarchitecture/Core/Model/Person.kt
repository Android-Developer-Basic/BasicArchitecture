package ru.otus.basicarchitecture.Core.Model

import ru.otus.basicarchitecture.DI.FragmentScope

@FragmentScope
data class Person(
    var firstName: String,
    var surName: String,
    var dateOfBirth: String
) : BaseModel  {
    companion object {
        const val defaultValueProperty = ""

        fun defaultPerson(): Person {
            return Person(
                defaultValueProperty,
                defaultValueProperty,
                defaultValueProperty
            )
        }
    }
}