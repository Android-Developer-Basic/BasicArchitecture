package ru.otus.basicarchitecture.data

import javax.inject.Inject
class TagsList  @Inject constructor() {
    val interests = listOf(
        "Board games",
        "Computer games",
        "Hiking",
        "Bird watching",
        "DIY",
        "Reading",
        "Parties"
    )
}