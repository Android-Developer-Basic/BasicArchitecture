package ru.otus.basicarchitecture.data

import javax.inject.Inject

class InterestsList @Inject constructor() {

    val interests = listOf(
        "Aquarium keeping",
        "Computer programming",
        "Creative writing",
        "Equestrianism",
        "Gaming",
        "Metal detecting",
        "People watching",
        "Sand castle building",
    )
}