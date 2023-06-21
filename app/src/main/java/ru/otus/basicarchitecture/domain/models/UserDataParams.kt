package ru.otus.basicarchitecture.domain.models



class UserName(val name :String = ""):ViewModelData

class UserSurname(val surname: String = ""):ViewModelData


class UserBirthDate(val birthDate: String = ""): ViewModelData


class UserHobby(val hobby: Set<String> = setOf()):ViewModelData

class UserAddress(
    val country: String = "",
    val city: String = "",
    val address: String = ""
):ViewModelData

