package ru.otus.basicarchitecture.domain.usecases.user_hobby_list_use_cases

import androidx.lifecycle.LiveData
import ru.otus.basicarchitecture.domain.models.HobbyItem
import ru.otus.basicarchitecture.domain.repository.HobbyRepository

class GetHobbyListUseCase(private val hobbyRepository: HobbyRepository) {
    fun getHobbyList(): LiveData<List<HobbyItem>> {
        return hobbyRepository.getHobbyList()
    }
}