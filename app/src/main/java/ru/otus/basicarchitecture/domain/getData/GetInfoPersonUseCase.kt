package ru.otus.basicarchitecture.domain.getData

import ru.otus.basicarchitecture.domain.Model.DomainModel
import ru.otus.basicarchitecture.domain.Repository
import javax.inject.Inject

class GetInfoPersonUseCase @Inject constructor(val repository: Repository) {
    fun getInfo() : Map<String, DomainModel>{
        return repository.getInfoPersonUseCase()
    }
}