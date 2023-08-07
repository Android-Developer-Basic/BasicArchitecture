package ru.otus.basicarchitecture.domain.repository

import androidx.lifecycle.LiveData
import ru.otus.basicarchitecture.domain.models.HobbyItem

interface HobbyRepository {
    fun getHobbyList(): LiveData<List<HobbyItem>>
    fun setHobby(item: HobbyItem)
}