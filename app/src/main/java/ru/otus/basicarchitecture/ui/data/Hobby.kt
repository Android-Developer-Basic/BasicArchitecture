package ru.otus.basicarchitecture.ui.data

import javax.inject.Inject

class Hobby @Inject constructor() {

    val hobby_sports = listOf(
        "велосипед", "бег", "футбол", "йога", "теннис", "баскетбол", "паркур", "бег",
    )

    val hobby_сreative = listOf(
        "музыка", "вокал", "кулинария", "батик", "вышивка", "рисование", "фотография"
    )

    val hobby_intelligence = listOf(
        "игры", "головоломки", "шахматы", "чтение", "наука"
    )
}