package ru.otus.basicarchitecture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.otus.basicarchitecture.Core.Model.Address
import ru.otus.basicarchitecture.Core.Model.BaseModel
import ru.otus.basicarchitecture.Core.Model.Interests
import ru.otus.basicarchitecture.Core.Model.Person
import ru.otus.basicarchitecture.Domain.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val wizardCache: WizardCache
) : Repository {


    private val listInterests =  listOf(
        "Йога и медитация",
        "Игры настольные",
        "Пение в караоке",
        "Реставрация старинной мебели",
        "Занятия танцами",
        "Развитие навыков программирования",
        "Садоводство и уход за растениями",
        "Фотография архитектурных сооружений",
        "Шитье и создание одежды",
        "Философия и обсуждение философских вопросов",
        "Развитие навыков игры на музыкальных инструментах",
        "Прослушивание подкастов о науке",
        "Рукоделие и создание поделок",
        "Экстримальные виды спорта: парашютный спорт, сноубординг, скейтбординг"
    )




    private val mutableLiveDataWizardCache = MutableLiveData<WizardCache>()

    val liveDataWizardCache: LiveData<WizardCache> = mutableLiveDataWizardCache



    override fun setPerson(person: Person) {
        wizardCache.firstName = person.firstName
        wizardCache.surName = person.surName
        wizardCache.dateOfBirth = person.firstName
        updateWizardCache()
    }

    override fun setAddress(address: Address) {
        wizardCache.address = address.address
        updateWizardCache()
    }

    private fun updateWizardCache() {
        mutableLiveDataWizardCache.postValue(wizardCache)
    }

    override fun getListInterests(): List<String>  = listInterests

    override fun setInterests(interests: Interests) {
        wizardCache.interests = interests.interests
        updateWizardCache()
    }

    override fun getInfoPersonUseCase(): Map<String, BaseModel> {
        val person = Person(
            wizardCache.firstName,
            wizardCache.surName,
            wizardCache.dateOfBirth
        )

        val address = Address(
            wizardCache.address
        )

        val interests = Interests(
            wizardCache.interests
        )


        return mapOf(
            "person" to person,
            "address" to address,
            "interests" to interests
        )
    }

}