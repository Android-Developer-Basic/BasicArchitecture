package ru.otus.basicarchitecture.domain.models

class User(
    val name:String,
    val surname:String,
    val birthDate:String,
    val country:String,
    val city:String,
    val address:String,
    val hobby:Set<String>
): ViewModelData