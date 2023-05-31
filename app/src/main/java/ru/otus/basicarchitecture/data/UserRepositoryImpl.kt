package ru.otus.basicarchitecture.data

import ru.otus.basicarchitecture.domain.models.User
import ru.otus.basicarchitecture.domain.models.UserAddress
import ru.otus.basicarchitecture.domain.models.UserAge
import ru.otus.basicarchitecture.domain.models.UserHobby
import ru.otus.basicarchitecture.domain.models.UserName
import ru.otus.basicarchitecture.domain.models.UserSurname
import ru.otus.basicarchitecture.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage):UserRepository {
    override fun getData():User {
       return userStorage.getData()
    }

    override fun saveUserName(userName: UserName) {
        userStorage.saveUserName(userName)
    }

    override fun saveUserSurname(userSurname: UserSurname) {
        userStorage.saveUserSurname(userSurname)
    }

    override fun saveUserAge(userAge: UserAge) {
        userStorage.saveUserAge(userAge)
    }

    override fun saveUserAddress(userAddress: UserAddress) {
        userStorage.saveUserAddress(userAddress)
    }

    override fun saveUserHobby(userHobby: UserHobby) {
        userStorage.saveUserHobby(userHobby)
    }


}