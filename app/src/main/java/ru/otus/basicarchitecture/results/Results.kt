package ru.otus.basicarchitecture.results

data class Results(
    val name: String = "",
    val surname: String = "",
    val birthdate: String = "",
    val address: String = "",
    val interests: List<String> = listOf()
)
