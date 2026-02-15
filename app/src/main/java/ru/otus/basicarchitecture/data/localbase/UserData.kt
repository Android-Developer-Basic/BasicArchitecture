package ru.otus.basicarchitecture.data.localbase

import ru.otus.basicarchitecture.domain.Interests

data class UserData(
    val name: String = "",
    val surname: String = "",
    val age: Int? = null,

    val address: String = "",

    val interests: List<Interests> = emptyList()
)