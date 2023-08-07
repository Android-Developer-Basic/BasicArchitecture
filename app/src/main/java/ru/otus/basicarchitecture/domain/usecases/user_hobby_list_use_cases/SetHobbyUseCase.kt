package ru.otus.basicarchitecture.domain.usecases.user_hobby_list_use_cases

import ru.otus.basicarchitecture.domain.models.HobbyItem
import ru.otus.basicarchitecture.domain.repository.HobbyRepository

class SetHobbyUseCase(private val hobbyRepository: HobbyRepository) {
    fun setHobby(hobbyItem: HobbyItem){
        hobbyRepository.setHobby(hobbyItem)
    }
}