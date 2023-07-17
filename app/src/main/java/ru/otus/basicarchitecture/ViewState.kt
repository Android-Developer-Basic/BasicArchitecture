package ru.otus.basicarchitecture

data class ViewState (
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: String = "",
    val country: String = "",
    val city: String = "",
    val address: String = "",
    val selectedTags: MutableList<String> = mutableListOf()
)