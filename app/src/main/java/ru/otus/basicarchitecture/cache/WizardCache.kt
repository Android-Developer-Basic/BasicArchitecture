package ru.otus.basicarchitecture.cache

import kotlinx.coroutines.flow.StateFlow
import ru.otus.basicarchitecture.model.Address
import ru.otus.basicarchitecture.model.PersonalInfo
import ru.otus.basicarchitecture.model.WizardData

interface WizardCache {
    val data: StateFlow<WizardData>

    fun savePersonalInfo(personalInfo: PersonalInfo)

    fun saveAddress(address: Address)

    fun saveInterests(interests: Set<String>)

    fun clear()
}