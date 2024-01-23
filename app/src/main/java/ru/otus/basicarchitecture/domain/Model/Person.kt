package ru.otus.basicarchitecture.domain.Model

import ru.otus.basicarchitecture.DI.FragmentComponents.FragmentScope


@FragmentScope
data class Person(
    var firstName: String,
    var surName: String,
    var dateOfBirth: String
) {
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
