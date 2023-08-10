package ru.otus.basicarchitecture.name

import java.util.Date

data class NameFragmentState (
    val name: String = "",
    val surname: String = "",
    val date: Date = Date(),
    val isAdult: Boolean = false,
    val accessNextButton: Boolean = false,
)