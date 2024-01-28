package ru.otus.basicarchitecture.domain

import ru.otus.basicarchitecture.domain.Model.Address
import ru.otus.basicarchitecture.domain.Model.Interests
import ru.otus.basicarchitecture.domain.Model.Person

interface Repository {

    fun setPerson(person: Person)

    fun setInterests(interests: Interests)


    fun setAddress(address: Address)


    fun getInfoPersonUseCase()
}