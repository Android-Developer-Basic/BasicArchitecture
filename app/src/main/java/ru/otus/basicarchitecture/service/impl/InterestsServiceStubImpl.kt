package ru.otus.basicarchitecture.service.impl

import ru.otus.basicarchitecture.service.InterestsService

class InterestsServiceStubImpl : InterestsService {

    override suspend fun getAvailableInterests(): Set<String> = setOf(
        "Cooking", "Hiking", "Programming", "Travelling", "Sleeping"
    )
}