package ru.otus.basicarchitecture.domain.setData

import ru.otus.basicarchitecture.domain.Model.Person
import ru.otus.basicarchitecture.domain.Repository

class SetPersonUseCase(val repository: Repository) {
    fun setPerson(person: Person) {
        repository.setPerson(person)
    }
}