package ru.otus.basicarchitecture.domain.repository

import ru.otus.basicarchitecture.domain.models.User
import ru.otus.basicarchitecture.domain.models.UserAddress
import ru.otus.basicarchitecture.domain.models.UserBirthDate
import ru.otus.basicarchitecture.domain.models.UserHobby
import ru.otus.basicarchitecture.domain.models.UserName
import ru.otus.basicarchitecture.domain.models.UserSurname

interface UserRepository {

    fun getData(): User

    fun saveUserName(userName:UserName)

    fun saveUserSurname(userSurname: UserSurname)

    fun saveUserBirthDate(userBirthDate: UserBirthDate)

    fun saveUserAddress(userAddress: UserAddress)

    fun saveUserHobby(userHobby: UserHobby)



}