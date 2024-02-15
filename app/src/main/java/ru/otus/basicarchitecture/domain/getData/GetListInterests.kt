package ru.otus.basicarchitecture.domain.getData

import ru.otus.basicarchitecture.domain.Repository
import javax.inject.Inject

class GetListInterests @Inject constructor(val repository: Repository) {
    fun getList(): List<String>{
        return repository.getListInterests()
    }
}