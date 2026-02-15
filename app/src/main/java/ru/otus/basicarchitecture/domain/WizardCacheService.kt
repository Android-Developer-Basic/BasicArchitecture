package ru.otus.basicarchitecture.domain

import ru.otus.basicarchitecture.data.localbase.UserData

interface WizardCacheService {
    fun updateUserPersonalInformation(name: String, surname: String, age: Int?)
    fun updateUserAddress(address: String)
    fun updateUserInterests(interests: List<Interests>)
    fun getUserInfo(): UserData
    fun clearUserInfo()
}