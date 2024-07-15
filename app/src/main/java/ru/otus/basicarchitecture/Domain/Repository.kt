package ru.otus.basicarchitecture.Domain

import ru.otus.basicarchitecture.Core.Model.Address
import ru.otus.basicarchitecture.Core.Model.BaseModel
import ru.otus.basicarchitecture.Core.Model.Interests
import ru.otus.basicarchitecture.Core.Model.Person

interface Repository {
    fun setPerson(person: Person)
    fun getInfoPersonUseCase() : Map<String, BaseModel>
    fun setAddress(address: Address)
    fun setInterests(interests: Interests)
    fun getListInterests(): List<String>
}