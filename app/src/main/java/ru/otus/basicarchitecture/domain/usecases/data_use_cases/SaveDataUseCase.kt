package ru.otus.basicarchitecture.domain.usecases.data_use_cases

import ru.otus.basicarchitecture.domain.models.UserName
import ru.otus.basicarchitecture.domain.models.UserAddress
import ru.otus.basicarchitecture.domain.models.UserBirthDate
import ru.otus.basicarchitecture.domain.models.UserHobby
import ru.otus.basicarchitecture.domain.models.UserSurname
import ru.otus.basicarchitecture.domain.models.ViewModelData
import ru.otus.basicarchitecture.domain.repository.UserRepository

class SaveDataUseCase(private val userRepository: UserRepository) {

    fun setUserName(userName: ViewModelData){
        userRepository.saveUserName(userName as UserName)
    }

    fun setUserSurname(userSurname: UserSurname){
        userRepository.saveUserSurname(userSurname)
    }

    fun setUserBirthDate(userBirthDate: UserBirthDate){
        userRepository.saveUserBirthDate(userBirthDate)

    }

    fun setUserAddress(userAddress: UserAddress){
        userRepository.saveUserAddress(userAddress)
    }

    fun setUserHobby(userHobby: UserHobby){
       userRepository.saveUserHobby(userHobby)
    }


}