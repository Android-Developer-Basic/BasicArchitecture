package ru.otus.basicarchitecture.data.localbase

import ru.otus.basicarchitecture.domain.Interests
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WizardCache @Inject constructor() {
    private var user = UserData()

    fun updateUserPersonalInformation(name: String, surname: String, age: Int?) {
        user = user.copy(name = name, surname = surname, age = age)
    }

    fun updateUserAddress(address: String) {
        user = user.copy(address = address)
    }

    fun updateUserInterests(interests: List<Interests>) {
        user = user.copy(interests = interests)
    }

    fun getUserInfo(): UserData {
        return user
    }

    fun clearUserInfo() {
        user = UserData()
    }
}