package ru.otus.basicarchitecture.data.localbase

import ru.otus.basicarchitecture.domain.Interests
import ru.otus.basicarchitecture.domain.WizardCacheService
import javax.inject.Inject

class WizardCacheServiceImpl @Inject constructor(private val wizardCache: WizardCache) :
    WizardCacheService {

    override fun updateUserPersonalInformation(
        name: String,
        surname: String,
        age: Int?
    ) {
        wizardCache.updateUserPersonalInformation(name = name, surname = surname, age = age)
    }

    override fun updateUserAddress(address: String) {
        wizardCache.updateUserAddress(address = address)
    }

    override fun updateUserInterests(interests: List<Interests>) {
        wizardCache.updateUserInterests(interests = interests)
    }

    override fun getUserInfo(): UserData = wizardCache.getUserInfo()

    override fun clearUserInfo() {
        wizardCache.clearUserInfo()
    }
}