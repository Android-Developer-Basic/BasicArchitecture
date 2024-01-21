package ru.otus.basicarchitecture.interests

import dagger.hilt.android.scopes.ViewModelScoped
import ru.otus.basicarchitecture.service.InterestsService
import javax.inject.Inject

@ViewModelScoped
class InterestsUseCase @Inject constructor() {

    @Inject
    lateinit var interestsService: InterestsService

    suspend fun getAvailableInterests(): Set<String> = interestsService.getAvailableInterests()

}