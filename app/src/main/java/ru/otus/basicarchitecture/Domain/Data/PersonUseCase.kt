package ru.otus.basicarchitecture.Domain.Data

import ru.otus.basicarchitecture.Core.Model.BaseModel
import ru.otus.basicarchitecture.Core.Model.Person
import ru.otus.basicarchitecture.Domain.Repository
import javax.inject.Inject

class PersonUseCase @Inject constructor(
    private val repository: Repository
) {
    fun setPerson(person: Person) {
        repository.setPerson(person)
    }

    fun getList(): List<String>{
        return repository.getListInterests()
    }

    fun getPerson(): Map<String, BaseModel> {
        return repository.getInfoPersonUseCase()
    }
}