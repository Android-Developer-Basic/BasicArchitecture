package ru.otus.basicarchitecture

import android.text.Editable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toBrithDateString(): String =
    format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

fun String.toBirthDate() : LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern("dd.MM.yyyy"))

fun Editable.toBirthDate() : LocalDate = toString().toBirthDate()