package ru.otus.basicarchitecture.repository

import ru.otus.basicarchitecture.model.Tag

class TagRepository {
    fun getTags(): List<Tag> {
        return listOf(
            Tag(1, "Музыка"),
            Tag(2, "Кошки"),
            Tag(3, "Кино"),
            Tag(4, "Фотография"),
            Tag(5, "Театр")
        )
    }
}