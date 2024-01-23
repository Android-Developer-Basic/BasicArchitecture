package ru.otus.basicarchitecture.domain.setData

import ru.otus.basicarchitecture.domain.Model.Interests
import ru.otus.basicarchitecture.domain.Repository
import javax.inject.Inject

class SetInterestsPersonUseCase @Inject constructor(
    private val repository: Repository
) {
    fun setInterests(interests: Interests){
        repository.setInterests(interests)
    }
}