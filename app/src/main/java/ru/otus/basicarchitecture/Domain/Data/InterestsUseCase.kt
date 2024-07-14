package ru.otus.basicarchitecture.Domain.Data

import ru.otus.basicarchitecture.Core.Model.Interests
import ru.otus.basicarchitecture.Domain.Repository
import javax.inject.Inject

class InterestsUseCase @Inject constructor(
    private val repository: Repository
) {
    fun setInterests(interests: Interests){
        repository.setInterests(interests)
    }

    fun getList(): List<String>{
        return repository.getListInterests()
    }
}