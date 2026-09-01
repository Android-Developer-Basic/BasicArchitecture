package ru.otus.basicarchitecture.interests

interface InterestsRepository {
    fun getInterests(): List<String>
}