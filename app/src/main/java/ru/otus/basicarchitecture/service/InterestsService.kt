package ru.otus.basicarchitecture.service

interface InterestsService {

    suspend fun getAvailableInterests(): Set<String>

}