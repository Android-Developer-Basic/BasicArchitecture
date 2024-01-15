package ru.otus.basicarchitecture.domain.setData

import ru.otus.basicarchitecture.domain.Model.Interests
import ru.otus.basicarchitecture.domain.Repository

class SetInterestsPersonUseCase(val repository: Repository) {
    fun setInterests(interests: Interests){
        repository.setInterests(interests)
    }
}