package ru.otus.basicarchitecture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.otus.basicarchitecture.domain.Model.Address
import ru.otus.basicarchitecture.domain.Model.Interests
import ru.otus.basicarchitecture.domain.Model.Person
import ru.otus.basicarchitecture.domain.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val wizardCache: WizardCache
) : Repository {

    private val mutableLiveDataWizardCache = MutableLiveData<WizardCache>()

    val liveDataWizardCache: LiveData<WizardCache> = mutableLiveDataWizardCache

    override fun setPerson(person: Person) {
        wizardCache.firstName = person.firstName
        wizardCache.surName = person.surName
        wizardCache.dateOfBirth = person.firstName
        updateWizardCache()
    }

    override fun setInterests(interests: Interests) {
        wizardCache.interests = interests.interests
        updateWizardCache()
    }

    override fun setAddress(address: Address) {
        wizardCache.city = address.city
        wizardCache.country = address.country
        wizardCache.address = address.address
        updateWizardCache()
    }

    private fun updateWizardCache() {
        mutableLiveDataWizardCache.postValue(wizardCache)
    }
}