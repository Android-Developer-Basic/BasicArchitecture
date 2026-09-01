package ru.otus.basicarchitecture.interests

import javax.inject.Inject

class DefaultInterestsRepository @Inject constructor() : InterestsRepository {
    override fun getInterests(): List<String> = listOf(
        "Путешествия",
        "Кино",
        "Музыка",
        "Спорт",
        "Книги",
        "Игры",
        "Технологии",
        "Искусство",
        "Кулинария",
        "Фотография",
        "Театр",
        "Танцы",
    )
}