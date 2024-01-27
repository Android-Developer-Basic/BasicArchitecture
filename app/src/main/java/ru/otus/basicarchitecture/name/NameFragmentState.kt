package ru.otus.basicarchitecture.name

import java.util.Date

data class NameFragmentViewState(
    val name: String = "",
    val surname: String = "",
    val date: Date = Date(),
    val accessNextButton: Boolean = false,
)