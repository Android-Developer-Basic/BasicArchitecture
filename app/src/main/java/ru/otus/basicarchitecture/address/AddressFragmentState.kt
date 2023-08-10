package ru.otus.basicarchitecture.address

data class AddressFragmentState (
    val country: String = "",
    val city: String = "",
    val address: String = "",
    val accessNextButton: Boolean = false,
)