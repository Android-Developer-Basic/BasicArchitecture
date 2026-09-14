package ru.otus.basicarchitecture.cache

import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.otus.basicarchitecture.model.Address
import ru.otus.basicarchitecture.model.PersonalInfo
import ru.otus.basicarchitecture.model.WizardData

class InMemoryWizardCache @Inject constructor() : WizardCache {
    private val mutableData = MutableStateFlow(WizardData())

    override val data: StateFlow<WizardData> = mutableData.asStateFlow()

    override fun savePersonalInfo(personalInfo: PersonalInfo) {
        mutableData.update { it.copy(personalInfo = personalInfo) }
    }

    override fun saveAddress(address: Address) {
        mutableData.update { it.copy(address = address) }
    }

    override fun saveInterests(interests: Set<String>) {
        mutableData.update { it.copy(interests = interests.toSet()) }
    }

    override fun clear() {
        mutableData.value = WizardData()
    }
}