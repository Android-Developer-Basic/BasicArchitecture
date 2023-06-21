package ru.otus.basicarchitecture.domain.usecases.data_use_cases

import ru.otus.basicarchitecture.domain.models.User
import ru.otus.basicarchitecture.domain.repository.UserRepository

class GetDataUseCase(private val userRepository: UserRepository) {
    fun getUser(): User {
        return userRepository.getData()
    }

}