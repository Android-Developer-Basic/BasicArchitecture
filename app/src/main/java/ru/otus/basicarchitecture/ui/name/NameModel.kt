package ru.otus.basicarchitecture.ui.name

import ru.otus.basicarchitecture.utils.DateTimeConverter
import java.util.Date

data class NameModel (
    val name: String = "",
    val surname: String = "",
    val birth: Date = Date(),
    val adult: Boolean = false
)